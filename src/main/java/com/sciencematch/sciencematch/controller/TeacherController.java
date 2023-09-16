package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.DTO.auth.request.StudentRequestDto;
import com.sciencematch.sciencematch.DTO.auth.response.StudentResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.SimpleStudentsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.MyStudentsResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.common.AuthService;
import com.sciencematch.sciencematch.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
@Tag(name = "선생", description = "선생 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class TeacherController {

    private final TeacherService teacherService;
    private final AuthService authService;

    @PostMapping(value = "/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "로고 변경")
    public ApiResponseDto<?> uploadLogo(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @ModelAttribute MultipartFile logo) throws IOException {
        teacherService.changeLogo(logo, user.getUsername());
        return ApiResponseDto.success(SuccessStatus.CHANGE_LOGO_SUCCESS);
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 조회")
    public ApiResponseDto<?> getMyPage(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS,
            teacherService.getMypage(user.getUsername()));
    }

    @PostMapping("/student/create")
    @Operation(summary = "학생 생성")
    public ApiResponseDto<StudentResponseDto> createStudent(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody StudentRequestDto studentRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_STUDENT_SUCCESS,
            authService.signupStudent(studentRequestDto, user.getUsername()));
    }

    @PostMapping("/student/update")
    @Operation(summary = "학생 정보 업데이트")
    public ApiResponseDto<StudentResponseDto> updateStudent(
        @RequestBody StudentRequestDto studentRequestDto) {
        return ApiResponseDto.success(SuccessStatus.STUDENT_INFO_UPDATE_SUCCESS,
            authService.updateStudent(studentRequestDto));
    }

    @DeleteMapping("/student/delete")
    @Operation(summary = "학생 삭제")
    public ApiResponseDto<StudentResponseDto> deleteStudent(
        @Schema(example = "01087654321") @RequestParam("phoneNum") String phoneNum) {
        return ApiResponseDto.success(SuccessStatus.DELETE_STUDENT_SUCCESS,
            authService.deleteStudent(phoneNum));
    }

    @GetMapping("/students")
    @Operation(summary = "나의 학생들 조회") //학생 관리 뷰에서 필요한 api
    public ApiResponseDto<List<MyStudentsResponseDto>> getStudents(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_STUDENTS_SUCCESS,
            teacherService.getMyStudents(user.getUsername()));
    }

    @GetMapping("/students/all")
    @Operation(summary = "간단 학생들 조회", description = "왼쪽에 간략하게 나와있는 탭 (추후 수정가능성 존재)")
    //학습지 등 간략하게 학생 리스트를 살펴볼 때 필요한 api
    public ApiResponseDto<List<SimpleStudentsResponseDto>> findAllStudents(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_STUDENT_SUCCESS,
            teacherService.findAllStudents(user.getUsername()));
    }
}
