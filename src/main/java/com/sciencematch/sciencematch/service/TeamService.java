package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.domain.TeamStudent;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.DTO.team.TeamDetailDto;
import com.sciencematch.sciencematch.DTO.team.TeamResponseDto;
import com.sciencematch.sciencematch.DTO.team.request.TeamRequestDto;
import com.sciencematch.sciencematch.infrastructure.TeamStudentRepository;
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
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TeamStudentRepository teamStudentRepository;

    //반 상세정보 조회
    public TeamDetailDto getGroupDetail(Long teamId) {
        return TeamDetailDto.of(teamRepository.getTeamById(teamId));
    }

    //반 상세정보 업데이트
    @Transactional
    public TeamDetailDto updateGroupDetail(Long groupId, TeamRequestDto teamRequestDto) {
        Team team = teamRepository.getTeamById(groupId);

        teamStudentRepository.deleteAll(team.getTeamStudents()); //기존 반 학생 연관관계 삭제
        team.updateGroupDetail(teamRequestDto.getTeamName());

        createGroupStudents(teamRequestDto, team);

        return TeamDetailDto.of(team);
    }

    //반 생성
    @Transactional
    public TeamResponseDto createGroup(String email, TeamRequestDto teamRequestDto) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);

        Team team = teamRepository.save(Team.builder()
            .name(teamRequestDto.getTeamName())
            .teacherName(teamRequestDto.getTeacherName())
            .teacher(teacher)
            .build());

        createGroupStudents(teamRequestDto, team);

        return TeamResponseDto.of(team);
    }

    private void createGroupStudents(TeamRequestDto teamRequestDto, Team team) {
        List<Student> students = teamRequestDto.getStudentIds().stream()
            .map(studentRepository::getStudentById)
            .collect(Collectors.toList());

        //groupstudent 생성 및 연관관계 세팅
        for (Student student : students) {
            setTeamStudents(team, student);
        }
    }

    private void setTeamStudents(Team team, Student student) {
        teamStudentRepository.save(TeamStudent.builder()
            .student(student)
            .team(team)
            .build());
    }

    @Transactional
    public void deleteGroup(List<Long> groupIds) {
        for (Long id : groupIds) {
            teamStudentRepository.deleteAllByTeamId(id);
        }
        teamStudentRepository.flush();
        teamRepository.deleteAllByIdInBatch(groupIds);
    }

}
