package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.dto.admin.AdminStudentResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.domain.enumerate.Authority;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public List<WaitingTeacherResponseDto> getAllWaitingTeachers() {
        return teacherRepository.findAllByAuthority(Authority.ROLE_GUEST).stream()
            .map(WaitingTeacherResponseDto::of).collect(
                Collectors.toList());
    }

    @Transactional
    public WaitingTeacherResponseDto assignTeacher(Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacher.assignTeacher();
        return WaitingTeacherResponseDto.of(teacher);
    }

    public List<WaitingTeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAllByAuthority(Authority.ROLE_TEACHER)
            .stream().map(WaitingTeacherResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public WaitingTeacherResponseDto deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacherRepository.delete(teacher);
        return WaitingTeacherResponseDto.of(teacher);
    }

    public List<AdminStudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream().map(AdminStudentResponseDto::of)
            .collect(Collectors.toList());
    }
}
