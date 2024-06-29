package com.sciencematch.sciencematch.controller.common;

import com.sciencematch.sciencematch.DTO.auth.request.DuplCheckDto;
import com.sciencematch.sciencematch.DTO.auth.request.PasswordDto;
import com.sciencematch.sciencematch.DTO.auth.request.StudentLoginRequestDto;
import com.sciencematch.sciencematch.DTO.auth.request.TeacherLoginRequestDto;
import com.sciencematch.sciencematch.DTO.auth.request.TeacherRequestDto;
import com.sciencematch.sciencematch.DTO.auth.response.TeacherResponseDto;
import com.sciencematch.sciencematch.DTO.auth.response.TokenDto;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.jwt.TokenProvider;
import com.sciencematch.sciencematch.service.common.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "회원 관리", description = "Auth 관련 API docs")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    public ApiResponseDto<TeacherResponseDto> signup(
        @RequestBody @Valid TeacherRequestDto teacherRequestDto) {
        return ApiResponseDto.success(SuccessStatus.SIGNUP_SUCCESS,
            authService.signup(teacherRequestDto));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인") //추후 DTO 변경 필요
    public ApiResponseDto<TokenDto> login(
        @RequestBody TeacherLoginRequestDto teacherLoginRequestDto) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS,
            authService.login(teacherLoginRequestDto));
    }

    @PostMapping("/login/student")
    @Operation(summary = "학생 로그인") //추후 DTO 변경 필요
    public ApiResponseDto<TokenDto> studentLogin(
        @RequestBody StudentLoginRequestDto studentLoginRequestDto) {
        return ApiResponseDto.success(SuccessStatus.LOGIN_SUCCESS,
            authService.studentLogin(studentLoginRequestDto));
    }

    /**
     * HttpServletRequest나 HttpServletResponse 객체가 Service 계층으로 넘어가는 것은 좋지 않다.
     * request, response는 컨트롤러 계층에서 사용되는 객체이며, Service 계층이 request와 response를 알 필요가 없다.
     */
    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<String> logout(@Parameter(hidden = true) HttpServletRequest request) {
        String accessToken = tokenProvider.resolveAccessToken(request);
        return ApiResponseDto.success(SuccessStatus.LOGOUT_SUCCESS,
            authService.logout(accessToken));
    }

    @PostMapping("/reissue")
    @Operation(summary = "액세스 토큰 재발행")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<TokenDto> reissue(@RequestHeader("Authorization") String refreshToken) {
        return ApiResponseDto.success(SuccessStatus.REISSUE_SUCCESS,
            authService.reissue(refreshToken));
    }

    @PostMapping("/dupl-check")
    @Operation(summary = "이메일 중복 체크")
    public ApiResponseDto<String> duplCheck(@RequestBody DuplCheckDto email) {
        return ApiResponseDto.success(SuccessStatus.CHECK_DUPL_EMAIL_SUCCESS,
            authService.duplCheck(email));
    }

    @PostMapping("/withdrawal")
    @Operation(summary = "회원 탈퇴")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<String> withdrawal(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.WITHDRAWAL_SUCCESS,
            authService.withdrawal(user.getUsername()));
    }

    @PostMapping("/student/check-pw")
    @Operation(summary = "학생 비밀번호 확인")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<?> checkStudentPW(
            @Parameter(hidden = true) @AuthenticationPrincipal User user, @RequestBody PasswordDto password) {
        authService.checkStudentPW(user.getUsername(), password.getPassword());
        return ApiResponseDto.success(SuccessStatus.CHECK_PW_SUCCESS);
    }

    @PostMapping("/student/change-pw")
    @Operation(summary = "학생 비밀번호 변경")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<?> changeStudentPW(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @RequestBody PasswordDto password) {
        authService.changeStudentPW(user.getUsername(), password.getPassword());
        return ApiResponseDto.success(SuccessStatus.CHANGE_PW_SUCCESS);
    }

    @PostMapping("/teacher/check-pw")
    @Operation(summary = "선생 비밀번호 확인")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<?> checkTeacherPW(
            @Parameter(hidden = true) @AuthenticationPrincipal User user, @RequestBody PasswordDto password) {
        authService.checkTeacherPW(user.getUsername(), password.getPassword());
        return ApiResponseDto.success(SuccessStatus.CHECK_PW_SUCCESS);
    }

    @PostMapping("/teacher/change-pw")
    @Operation(summary = "선생 비밀번호 변경")
    @SecurityRequirement(name = "JWT Auth")
    public ApiResponseDto<?> changeTeacherPW(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @RequestBody PasswordDto password) {
        authService.changeTeacherPW(user.getUsername(), password.getPassword());
        return ApiResponseDto.success(SuccessStatus.CHANGE_PW_SUCCESS);
    }
}
