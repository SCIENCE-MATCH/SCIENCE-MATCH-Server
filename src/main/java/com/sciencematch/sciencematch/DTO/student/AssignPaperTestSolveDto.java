package com.sciencematch.sciencematch.DTO.student;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class AssignPaperTestSolveDto {

    @Schema(example = "57")
    private Long assignPaperTestId;
    private List<String> answer;

}
