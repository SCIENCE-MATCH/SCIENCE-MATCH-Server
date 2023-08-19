package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.domain.dto.group.GroupDetailDto;
import com.sciencematch.sciencematch.domain.dto.group.GroupResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.GroupService;
import com.sciencematch.sciencematch.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class GroupController {

    private final TeacherService teacherService;
    private final GroupService groupService;

    //나의
    @GetMapping("/group")
    @Operation(summary = "나의 반 목록 조회")
    public ApiResponseDto<List<GroupResponseDto>> getMyGroups(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MY_GROUPS_SUCCESS,
            teacherService.getMyGroups(user.getUsername()));
    }

    @GetMapping("/group/detail")
    @Operation(summary = "반 상세 정보 조회")
    public ApiResponseDto<GroupDetailDto> getGroupDetail(@Schema(example = "1") @RequestParam Long groupId) {
        return ApiResponseDto.success(SuccessStatus.GET_GROUP_DETAIL_SUCCESS,
            groupService.getGroupDetail(groupId));
    }
}
