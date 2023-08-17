package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.controller.dto.response.MyPageDto;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final S3Service s3Service;

    @Transactional
    public void changeLogo(MultipartFile logo, String email) throws IOException {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        if (teacher.getLogo() != null) s3Service.deleteFile(teacher.getLogo());
        String logoURL = s3Service.uploadImage(logo, "logo");
        teacher.changeLogo(logoURL);
    }

    public MyPageDto getMypage(String email) {
        return MyPageDto.of(teacherRepository.getTeacherByEmail(email));
    }
}
