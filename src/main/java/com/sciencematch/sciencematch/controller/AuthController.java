package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.controller.dto.request.DuplCheckDto;
import com.sciencematch.sciencematch.controller.dto.request.TeacherRequestDto;
import com.sciencematch.sciencematch.controller.dto.request.StudentLoginRequestDto;
import com.sciencematch.sciencematch.controller.dto.request.TeacherLoginRequestDto;
import com.sciencematch.sciencematch.controller.dto.response.TeacherResponseDto;
import com.sciencematch.sciencematch.controller.dto.response.TokenDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.jwt.TokenProvider;
import com.sciencematch.sciencematch.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @SecurityRequirements({
        @SecurityRequirement(name = "JWT Auth"),
        @SecurityRequirement(name = "Refresh")
    })
    public ApiResponseDto<TokenDto> reissue(@Parameter(hidden = true) HttpServletRequest request) {
        String accessToken = tokenProvider.resolveAccessToken(request);
        String refreshToken = tokenProvider.resolveRefreshToken(request);
        return ApiResponseDto.success(SuccessStatus.REISSUE_SUCCESS,
            authService.reissue(accessToken, refreshToken));
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
}
