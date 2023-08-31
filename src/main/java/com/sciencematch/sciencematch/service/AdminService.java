package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.domain.dto.admin.AdminStudentResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.AdminTeamResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.domain.enumerate.Authority;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import com.sciencematch.sciencematch.infrastructure.TeamRepository;
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
    private final TeamRepository teamRepository;

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

    //현재는 teacher의 name을 가져오도록 했지만, 나중엔 teacher의 academy를 가져와야 할 수도
    public List<AdminStudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream().map(AdminStudentResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public AdminStudentResponseDto deleteStudent(Long id) {
        Student student = studentRepository.getStudentById(id);
        studentRepository.delete(student);
        return AdminStudentResponseDto.of(student);
    }

    public List<AdminTeamResponseDto> getAllTeams() {
        return teamRepository.findAll().stream().map(AdminTeamResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public AdminTeamResponseDto deleteTeam(Long id) {
        Team team = teamRepository.getTeamById(id);
        teamRepository.delete(team);
        return AdminTeamResponseDto.of(team);
    }
}
