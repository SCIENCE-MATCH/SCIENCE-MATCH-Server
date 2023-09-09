package com.sciencematch.sciencematch.domain.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class QuestionPaperSubmitDto {

    @Schema(example = "[3, 4]", type = "array")
    private List<Long> studentIds;
    @Schema(example = "18")
    private Long questionPaperId;

}