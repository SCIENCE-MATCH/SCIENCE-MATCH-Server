package com.sciencematch.sciencematch.service.common;

import com.sciencematch.sciencematch.dto.auth.request.DuplCheckDto;
import com.sciencematch.sciencematch.dto.auth.request.StudentLoginRequestDto;
import com.sciencematch.sciencematch.dto.auth.request.StudentRequestDto;
import com.sciencematch.sciencematch.dto.auth.request.TeacherLoginRequestDto;
import com.sciencematch.sciencematch.dto.auth.request.TeacherRequestDto;
import com.sciencematch.sciencematch.dto.auth.response.StudentResponseDto;
import com.sciencematch.sciencematch.dto.auth.response.TeacherResponseDto;
import com.sciencematch.sciencematch.dto.auth.response.TokenDto;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.domain.Admin;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.TeacherLevel;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import com.sciencematch.sciencematch.exception.model.ExistEmailException;
import com.sciencematch.sciencematch.exception.model.LogoutRefreshtokenException;
import com.sciencematch.sciencematch.infrastructure.AdminRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherLevelRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import com.sciencematch.sciencematch.jwt.TokenProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TeacherRepository teacherRepository;
    private final TeacherLevelRepository teacherLevelRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    @Transactional
    public TeacherResponseDto signup(TeacherRequestDto teacherRequestDto) {
        if (teacherRepository.existsByEmail(teacherRequestDto.getEmail())) {
            throw new ExistEmailException(ErrorStatus.ALREADY_EXIST_USER_EXCEPTION,
                ErrorStatus.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }

        Teacher saveTeacher = teacherRepository.save(teacherRequestDto.toTeacher(passwordEncoder));
        setTeacherLevel(saveTeacher);
        Admin admin = adminRepository.getAdminById(1L);
        admin.getWaitingTeacher().add(saveTeacher); //권한은 Guest인 상태로 Teacher를 만들어 admin에 저장

        return TeacherResponseDto.of(saveTeacher);
    }

    private void setTeacherLevel(Teacher teacher) {
        teacherLevelRepository.save(new TeacherLevel(null, Level.HARD, 0.0, 0.0, 0.3, 0.3, 0.4, teacher));
        teacherLevelRepository.save(new TeacherLevel(null, Level.MEDIUM_HARD, 0.0, 0.2, 0.3, 0.3, 0.2, teacher));
        teacherLevelRepository.save(new TeacherLevel(null, Level.MEDIUM, 0.05, 0.3, 0.3, 0.25, 0.1, teacher));
        teacherLevelRepository.save(new TeacherLevel(null, Level.MEDIUM_LOW, 0.2, 0.4, 0.3, 0.1, 0.0, teacher));
        teacherLevelRepository.save(new TeacherLevel(null, Level.LOW, 0.4, 0.4, 0.2, 0.0, 0.0, teacher));
    }

    @Transactional
    public StudentResponseDto signupStudent(StudentRequestDto studentRequestDto, String email) {
        if (studentRepository.existsByPhoneNum(studentRequestDto.getPhoneNum())) {
            throw new ExistEmailException(ErrorStatus.ALREADY_EXIST_USER_EXCEPTION,
                ErrorStatus.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        return StudentResponseDto.of(studentRepository.save(studentRequestDto.toStudent(teacher)));
    }

    @Transactional
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto) {
        Student student = studentRepository.getStudentByPhoneNum(
            studentRequestDto.getPhoneNum());

        return StudentResponseDto.of(student.changeInfo(studentRequestDto));
    }

    @Transactional
    public StudentResponseDto deleteStudent(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        StudentResponseDto of = StudentResponseDto.of(student);
        studentRepository.delete(student);
        return of;
    }

    @Transactional
    public void reEnrollStudent(Long studentId) {
        studentRepository.reEnrollStudent(studentId);
    }

    @Transactional
    public String duplCheck(DuplCheckDto email) {

        if (teacherRepository.existsByEmail(email.getEmail())) {
            throw new ExistEmailException(ErrorStatus.ALREADY_EXIST_USER_EXCEPTION,
                ErrorStatus.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }
        return "사용 가능한 이메일입니다.";
    }

    @Transactional
    public TokenDto login(TeacherLoginRequestDto teacherLoginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = teacherLoginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // ID 존재 여부 + 해당 ID로 불러온 비밀번호가 사용자가 제출한 비밀번호와 일치하는지 겅즘
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        redisTemplate.opsForValue().set("RT:" + authentication.getName(),
            tokenDto.getRefreshToken(),
            tokenProvider.getRefreshTokenExpireTime(),
            TimeUnit.MILLISECONDS);

        // 5. 토큰 발급
        return tokenDto;
    }

    //학생 로그인
    @Transactional
    public TokenDto studentLogin(StudentLoginRequestDto studentLoginRequestDto) {
        studentRepository.getStudentByPhoneNum(studentLoginRequestDto.getPhoneNum());

        //권한 부여
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        //수동으로 user, password 토큰 생성 -> 인증 구현
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            studentLoginRequestDto.getPhoneNum(), studentLoginRequestDto.getPassword(), list);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        redisTemplate.opsForValue().set("RT:" + authentication.getName(),
            tokenDto.getRefreshToken(),
            tokenProvider.getRefreshTokenExpireTime(),
            TimeUnit.MILLISECONDS);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public String logout(String accessToken) {
        // 1. Access Token 검증
        if (!tokenProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorStatus.INVALID_ACCESS_TOKEN_EXCEPTION,
                ErrorStatus.INVALID_ACCESS_TOKEN_EXCEPTION.getMessage());
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = tokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue()
            .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        // 5. 토큰 발급
        return "로그아웃 되었습니다.";
    }

    @Transactional
    public TokenDto reissue(String refreshToken) {

        String target = parseTokenString(refreshToken);
        // 1. Refresh Token 검증
        tokenProvider.validateToken(target);

        // 2. Access Token 에서 Member ID(user email) 가져오기
        Authentication authentication = tokenProvider.getAuthentication(target);
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String existRefreshToken = (String) redisTemplate.opsForValue()
            .get("RT:" + authentication.getName());

        //로그아웃 되어 Redis에 RefreshToken이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(existRefreshToken)) {
            throw new LogoutRefreshtokenException(ErrorStatus.LOGOUT_REFRESH_TOKEN_EXCEPTION,
                ErrorStatus.LOGOUT_REFRESH_TOKEN_EXCEPTION.getMessage());
        }

        // 4. Refresh Token 일치하는지 검사
        if (!existRefreshToken.equals(target)) {
            throw new RuntimeException("Refresh Token의 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(),
            tokenDto.getRefreshToken(),
            tokenProvider.getRefreshTokenExpireTime(),
            TimeUnit.MILLISECONDS);

        //토큰발급
        return tokenDto;
    }

    private static String parseTokenString(String tokenString) {
        String[] strings = tokenString.split(" ");
        if (strings.length != 2) {
            throw new CustomException(ErrorStatus.INVALID_TOKEN_INFO_EXCEPTION, ErrorStatus.INVALID_TOKEN_INFO_EXCEPTION.getMessage());
        }
        return strings[1];
    }

    @Transactional
    public String withdrawal(String email) {
        teacherRepository.delete(teacherRepository.getTeacherByEmail(email));
        return "회원 탈퇴에 성공하였습니다";
    }

    public void checkStudentPW(String phoneNum, String password) {
        if (studentRepository.getStudentByPhoneNum(phoneNum).getPassword().equals(password)) {
            return;
        }
        throw new CustomException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, "비밀번호가 일치하지 않습니다.");
    }

    @Transactional
    public void changeStudentPW(String phoneNum, String password) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        student.changePW(passwordEncoder.encode(password));
    }

    public void checkTeacherPW(String email, String password) {
        if (teacherRepository.getTeacherByEmail(email).getPassword().equals(password)) {
            return;
        }
        throw new CustomException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, "비밀번호가 일치하지 않습니다.");
    }

    @Transactional
    public void changeTeacherPW(String email, String password) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        teacher.changePW(passwordEncoder.encode(password));
    }
}
