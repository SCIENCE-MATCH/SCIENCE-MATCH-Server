package com.sciencematch.sciencematch.domain.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class MultipleQuestionPaperSubmitDto {

    @Schema(type = "array", example = "[3, 4]")
    private List<Long> studentIds;

    @Schema(type = "array", example = "[18, 19]")
    private List<Long> QuestionPaperIds;

}
