package com.sciencematch.sciencematch.controller.dto.request;

import com.sciencematch.sciencematch.domain.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentRequestDto {

    @Schema(description = "학년", example = "고1")
    private String grade;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "이름 형식에 맞지 않습니다.")
    @Schema(description = "이름", example = "김사피")
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @Schema(description = "핸드폰 번호", example = "01012345679")
    private String parentNum;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    @Schema(description = "핸드폰 번호", example = "01087654321")
    private String phoneNum;

    @Schema(description = "생년월일", example = "20081120")
    private String birth;

    public Student toStudent() {
        return Student.builder()
            .grade(grade)
            .name(name)
            .phoneNum(phoneNum)
            .parentNum(parentNum)
            .birth(birth)
            .build();
    }

}
