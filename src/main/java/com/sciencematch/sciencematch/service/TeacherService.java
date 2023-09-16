package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.auth.response.MyPageDto;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.DTO.team.TeamResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.SimpleStudentsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.MyStudentsResponseDto;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final S3Service s3Service;

    //로고 변경
    @Transactional
    public void changeLogo(MultipartFile logo, String email) throws IOException {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        if (teacher.getLogo() != null) {
            s3Service.deleteFile(teacher.getLogo());
        }
        String logoURL = s3Service.uploadImage(logo, "logo");
        teacher.changeLogo(logoURL);
    }

    //학생 관리 조회
    public List<MyStudentsResponseDto> getMyStudents(String email) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        return studentRepository.findAllByTeacher(teacher).stream().map(MyStudentsResponseDto::of)
            .collect(Collectors.toList());
    }

    //마이페이지 조회
    public MyPageDto getMypage(String email) {
        return MyPageDto.of(teacherRepository.getTeacherByEmail(email));
    }

    //간단 학생 조회 (반 생성, 퀴즈 등)
    public List<SimpleStudentsResponseDto> findAllStudents(String email) {
        return teacherRepository.getTeacherByEmail(email).getStudents().stream().map(
                SimpleStudentsResponseDto::of)
            .collect(Collectors.toList());
    }

    //나의 반 목록 조회
    public List<TeamResponseDto> getMyGroups(String email) {
            return teacherRepository.getTeacherByEmail(email).getTeam().stream()
                .map(TeamResponseDto::of)
                .collect(Collectors.toList());
    }
}
