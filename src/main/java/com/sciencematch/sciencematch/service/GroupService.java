package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.GroupStudent;
import com.sciencematch.sciencematch.domain.Groups;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.dto.groups.GroupDetailDto;
import com.sciencematch.sciencematch.domain.dto.groups.GroupResponseDto;
import com.sciencematch.sciencematch.domain.dto.groups.request.GroupRequestDto;
import com.sciencematch.sciencematch.infrastructure.GroupRepository;
import com.sciencematch.sciencematch.infrastructure.GroupStudentRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupStudentRepository groupStudentRepository;

    //반 상세정보 조회
    public GroupDetailDto getGroupDetail(Long groupId) {
        return GroupDetailDto.of(groupRepository.getGroupById(groupId));
    }

    //반 상세정보 업데이트
    @Transactional
    public GroupDetailDto updateGroupDetail(Long groupId, GroupRequestDto groupRequestDto) {
        Groups groups = groupRepository.getGroupById(groupId);

        groupStudentRepository.deleteAll(groups.getGroupStudents()); //기존 반 학생 연관관계 삭제
        groups.updateGroupDetail(groupRequestDto.getGroupName());

        createGroupStudents(groupRequestDto, groups);

        return GroupDetailDto.of(groups);
    }

    //반 생성
    @Transactional
    public GroupResponseDto createGroup(String email, GroupRequestDto groupRequestDto) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);

        Groups groups = groupRepository.save(Groups.builder()
            .name(groupRequestDto.getGroupName())
            .teacher(teacher)
            .build());

        createGroupStudents(groupRequestDto, groups);

        return GroupResponseDto.of(groups);
    }

    private void createGroupStudents(GroupRequestDto groupRequestDto, Groups groups) {
        List<Student> students = groupRequestDto.getStudentIds().stream()
            .map(studentRepository::getStudentById)
            .collect(Collectors.toList());

        //groupstudent 생성 및 연관관계 세팅
        for (Student student : students) {
            setGroupStudents(groups, student);
        }
    }

    private void setGroupStudents(Groups groups, Student student) {
        groupStudentRepository.save(GroupStudent.builder()
            .student(student)
            .groups(groups)
            .build());
    }

}
