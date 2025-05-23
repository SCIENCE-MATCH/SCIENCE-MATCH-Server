package com.sciencematch.sciencematch.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignQuestionPaperSolveDto {

    @Schema(example = "52")
    @NotNull
    private Long assignQuestionPaperId;

    @Schema(example = "[\"1\", \"돈줘\"]", type = "array")
    private List<String> answer;

}
