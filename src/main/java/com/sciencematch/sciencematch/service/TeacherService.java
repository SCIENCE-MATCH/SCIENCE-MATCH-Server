package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Transactional
    public void changeLogo(String email, String logoURL) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        teacher.changeLogo(logoURL);
    }

//    public MyPageDto getMypage(String email) {
//
//    }
}
