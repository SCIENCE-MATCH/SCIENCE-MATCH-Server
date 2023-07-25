package com.sciencematch.sciencematch.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {
    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotBlank
    private String email;

    @NotNull
    @Pattern(
            regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}",
            message = "비밀번호는 영문과 숫자가 포함된 8자 ~ 16자의 비밀번호여야 합니다"
    )
    private String password;
}
