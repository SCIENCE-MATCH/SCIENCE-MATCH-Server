package com.sciencematch.sciencematch.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignPaperTestSolveDto {

    @Schema(example = "57")
    @NotNull
    private Long assignPaperTestId;

    @Schema(example = "이게 답일까?")
    private String answer;

}
