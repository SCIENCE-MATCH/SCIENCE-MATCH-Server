package com.sciencematch.sciencematch.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentLoginRequestDto {
    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @Schema(description = "핸드폰 번호", example = "01087654321")
    private String phoneNum;

    @NotBlank
    @Schema(description = "비밀번호", example = "01087654321")
    private String password;
}
