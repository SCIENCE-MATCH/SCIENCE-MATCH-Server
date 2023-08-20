package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.domain.dto.groups.GroupDetailDto;
import com.sciencematch.sciencematch.domain.dto.groups.GroupResponseDto;
import com.sciencematch.sciencematch.domain.dto.groups.request.GroupRequestDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.GroupService;
import com.sciencematch.sciencematch.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
@Tag(name = "반", description = "반 관리 API")
@SecurityRequirement(name = "JWT Auth")
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
    public ApiResponseDto<GroupDetailDto> getGroupDetail(
        @Schema(example = "1") @RequestParam Long groupId) {
        return ApiResponseDto.success(SuccessStatus.GET_GROUP_DETAIL_SUCCESS,
            groupService.getGroupDetail(groupId));
    }

    @PatchMapping("/group/update")
    @Operation(summary = "반 상세 정보 업데이트")
    public ApiResponseDto<GroupDetailDto> updateGroupDetail(
        @Schema(example = "3") @RequestParam Long groupId,
        @RequestBody GroupRequestDto groupRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_GROUP_DETAIL_SUCCESS,
            groupService.updateGroupDetail(groupId, groupRequestDto));
    }

    @PostMapping("/group")
    @Operation(summary = "반 생성")
    public ApiResponseDto<GroupResponseDto> createGroup(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody GroupRequestDto groupRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_GROUP_SUCCESS,
            groupService.createGroup(user.getUsername(), groupRequestDto));
    }
}
