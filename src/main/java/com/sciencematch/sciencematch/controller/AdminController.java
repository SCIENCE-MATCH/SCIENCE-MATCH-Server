package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.AdminStudentResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.AdminTeamResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/teachers/waiting")
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

    @GetMapping("/teachers")
    @Operation(summary = "선생 리스트 조회")
    public ApiResponseDto<List<WaitingTeacherResponseDto>> getAllTeachers() {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_TEACHERS_SUCCESS,
            adminService.getAllTeachers());
    }

    @DeleteMapping("/teachers/{id}")
    @Operation(summary = "선생 삭제")
    public ApiResponseDto<WaitingTeacherResponseDto> getAllTeacher(@PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.DELETE_TEACHER_SUCCESS,
            adminService.deleteTeacher(id));
    }

    @GetMapping("/students")
    @Operation(summary = "학생 리스트 조회")
    public ApiResponseDto<List<AdminStudentResponseDto>> getAllStudents() {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_STUDENTS_SUCCESS,
            adminService.getAllStudents());
    }

    @DeleteMapping("/students/{id}")
    @Operation(summary = "학생 삭제")
    public ApiResponseDto<AdminStudentResponseDto> deleteStudent(@PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.DELETE_STUDENT_SUCCESS,
            adminService.deleteStudent(id));
    }

    @GetMapping("/teams")
    @Operation(summary = "반 리스트 조회")
    public ApiResponseDto<List<AdminTeamResponseDto>> getAllTeams() {
        return ApiResponseDto.success(SuccessStatus.GET_TEAMS_SUCCESS,
            adminService.getAllTeams());
    }

    @DeleteMapping("/teams/{id}")
    @Operation(summary = "반 삭제")
    public ApiResponseDto<AdminTeamResponseDto> deleteTeam(@PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.DELETE_TEAM_SUCCESS,
            adminService.deleteTeam(id));
    }
}
