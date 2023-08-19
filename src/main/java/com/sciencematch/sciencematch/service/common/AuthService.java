package com.sciencematch.sciencematch.service.common;

import com.sciencematch.sciencematch.controller.dto.request.DuplCheckDto;
import com.sciencematch.sciencematch.controller.dto.request.StudentLoginRequestDto;
import com.sciencematch.sciencematch.controller.dto.request.StudentRequestDto;
import com.sciencematch.sciencematch.controller.dto.request.TeacherLoginRequestDto;
import com.sciencematch.sciencematch.controller.dto.request.TeacherRequestDto;
import com.sciencematch.sciencematch.controller.dto.response.StudentResponseDto;
import com.sciencematch.sciencematch.controller.dto.response.TeacherResponseDto;
import com.sciencematch.sciencematch.controller.dto.response.TokenDto;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import com.sciencematch.sciencematch.exception.model.ExistEmailException;
import com.sciencematch.sciencematch.exception.model.LogoutRefreshtokenException;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
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
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    @Transactional
    public TeacherResponseDto signup(TeacherRequestDto teacherRequestDto) {
        if (teacherRepository.existsByEmail(teacherRequestDto.getEmail())) {
            throw new ExistEmailException(ErrorStatus.ALREADY_EXIST_USER_EXCEPTION,
                ErrorStatus.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }

        return TeacherResponseDto.of(
            teacherRepository.save(teacherRequestDto.toTeacher(passwordEncoder)));
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
            studentLoginRequestDto.getPhoneNum(), null, list);

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
    public TokenDto reissue(String accessToken, String refreshToken) {
        // 1. Refresh Token 검증
        tokenProvider.validateToken(refreshToken);

        // 2. Access Token 에서 Member ID(user email) 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String existRefreshToken = (String) redisTemplate.opsForValue()
            .get("RT:" + authentication.getName());

        //로그아웃 되어 Redis에 RefreshToken이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(existRefreshToken)) {
            throw new LogoutRefreshtokenException(ErrorStatus.LOGOUT_REFRESH_TOKEN_EXCEPTION,
                ErrorStatus.LOGOUT_REFRESH_TOKEN_EXCEPTION.getMessage());
        }

        // 4. Refresh Token 일치하는지 검사
        if (!existRefreshToken.equals(refreshToken)) {
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

    @Transactional
    public String withdrawal(String email) {
        teacherRepository.delete(teacherRepository.getTeacherByEmail(email));
        return "회원 탈퇴에 성공하였습니다";
    }
}
