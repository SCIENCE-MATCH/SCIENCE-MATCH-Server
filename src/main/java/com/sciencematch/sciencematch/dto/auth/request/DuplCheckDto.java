package com.sciencematch.sciencematch.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DuplCheckDto {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotNull
    @Schema(description = "이메일 확인", example = "member@gmail.com")
    String email;
}
