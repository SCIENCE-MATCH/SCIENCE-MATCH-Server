package com.sciencematch.sciencematch.dto.student;

import com.sciencematch.sciencematch.domain.paper_test.PaperTestAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaperTestAnswerResponseDto {

    private Long id;

    private String submitAnswer;
    private String solution;
    private Boolean rightAnswer;

    public static PaperTestAnswerResponseDto of(PaperTestAnswer paperTestAnswer) {
        return new PaperTestAnswerResponseDto(paperTestAnswer.getId(),
            paperTestAnswer.getSubmitAnswer(), paperTestAnswer.getSolution(),
            paperTestAnswer.getRightAnswer());
    }

}
