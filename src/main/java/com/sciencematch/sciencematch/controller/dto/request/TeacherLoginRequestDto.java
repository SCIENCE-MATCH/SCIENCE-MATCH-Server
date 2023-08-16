package com.sciencematch.sciencematch.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherLoginRequestDto {
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotBlank
    @Schema(description = "유저 아이디(이메일 주소)", example = "science@gmail.com")
    private String email;

    @NotNull
    @Pattern(
            regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}",
            message = "비밀번호는 영문과 숫자가 포함된 8자 ~ 16자의 비밀번호여야 합니다"
    )
    @Schema(description = "비밀번호", example = "test1234")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
