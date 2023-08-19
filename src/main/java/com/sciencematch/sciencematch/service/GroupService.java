package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.dto.group.GroupDetailDto;
import com.sciencematch.sciencematch.infrastructure.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final TeacherService teacherService;

    //반 상세정보 조회
    public GroupDetailDto getGroupDetail(Long groupId) {
        return GroupDetailDto.of(groupRepository.getGroupById(groupId), teacherService.findAllStudents());
    }

}
