package com.sciencematch.sciencematch.dto.auth.request;

import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.enums.Authority;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원가입 DTO")
public class TeacherRequestDto {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotNull
    @Schema(description = "유저 아이디(이메일 주소)", example = "history@gmail.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "이름 형식에 맞지 않습니다.")
    @Schema(description = "이름", example = "단군")
    private String name;

    @NotBlank
    @Pattern(
        regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}",
        message = "비밀번호는 영문과 숫자가 포함된 8자 ~ 16자의 비밀번호여야 합니다."
    )
    @Schema(description = "비밀번호", example = "test12354")
    private String password;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @Schema(description = "핸드폰 번호", example = "01012232478")
    private String phoneNum;


    public Teacher toTeacher(PasswordEncoder passwordEncoder) {
        return Teacher.builder()
            .email(email)
            .name(name)
            .password(passwordEncoder.encode(password))
            .phoneNum(phoneNum)
            .authority(Authority.ROLE_GUEST)
            .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
