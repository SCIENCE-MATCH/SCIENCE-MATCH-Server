package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.infrastructure.AdminRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;

    public List<WaitingTeacherResponseDto> getAllWaitingTeachers() {
        return adminRepository.getAdminById(1L).getWaitingTeacher().stream()
            .map(WaitingTeacherResponseDto::of).collect(
                Collectors.toList());
    }

    @Transactional
    public WaitingTeacherResponseDto assignTeacher(Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacher.assignTeacher();
        return WaitingTeacherResponseDto.of(teacher);
    }
}