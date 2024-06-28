package com.sciencematch.sciencematch.DTO.student;

import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.domain.question.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponseDto {

    private Long id;

    private String submitAnswer;
    private String solution;
    private String solutionImg;
    private String questionImg;

    private Category category;
    private Boolean rightAnswer;

    public static AnswerResponseDto of(Answer answer) {
        return new AnswerResponseDto(answer.getId(), answer.getSubmitAnswer(), answer.getSolution(),
            answer.getSolutionImg(), answer.getQuestionImg(), answer.getCategory(), answer.getRightAnswer());
    }
}
