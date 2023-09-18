package com.sciencematch.sciencematch.DTO.student;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class AssignQuestionPaperSolveDto {

    @Schema(example = "52")
    private Long assignQuestionPaperId;
    private List<String> answer;

}
