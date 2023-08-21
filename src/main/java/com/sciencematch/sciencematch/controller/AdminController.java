package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@SecurityRequirement(name = "JWT Auth")
@Tag(name = "관리자", description = "관리자 API")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/teachers")
    @Operation(summary = "승인 대기 선생 조회")
    public ApiResponseDto<List<WaitingTeacherResponseDto>> getAllWaitingTeachers() {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_WAITING_TEACHERS_SUCCESS,
            adminService.getAllWaitingTeachers());
    }

    @PostMapping("/teachers/{id}")
    @Operation(summary = "선생 가입 승인")
    public ApiResponseDto<WaitingTeacherResponseDto> assignTeacher(
        @PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.ASSIGN_TEACHER_SUCCESS,
            adminService.assignTeacher(id));
    }
}
