package com.sciencematch.sciencematch.domain.dto.paper_test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PaperTestQuestionDto {

    @Schema(example = "test 질문")
    private String question;
    @Schema(example = "test 답")
    private String solution;

}
