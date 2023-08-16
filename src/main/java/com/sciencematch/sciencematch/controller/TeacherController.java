package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
@Tag(name = "선생", description = "선생 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class TeacherController {

    private final TeacherService teacherService;

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
    public ApiResponseDto<?> getMyPage(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS, teacherService.getMypage(user.getUsername()));
    }
}
