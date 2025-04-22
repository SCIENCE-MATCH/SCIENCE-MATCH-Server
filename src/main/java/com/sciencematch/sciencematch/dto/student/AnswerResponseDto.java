package com.sciencematch.sciencematch.dto.student;

import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponseDto {

    private Long id;
    private Long questionId;

    private String submitAnswer;
    private String solution;
    private String solutionImg;
    private String questionImg;
    private Integer score;
    private Level level;

    private Category category;
    private Boolean rightAnswer;

    private Long chapterId;
    private String chapterDescription;

    public static AnswerResponseDto of(Answer answer, Question question, Chapter chapter) {
        return new AnswerResponseDto(answer.getId(), question.getId(), answer.getSubmitAnswer(), answer.getSolution(),
            answer.getSolutionImg(), answer.getQuestionImg(), answer.getScore(), question.getLevel(), answer.getCategory(), answer.getRightAnswer(),
            chapter.getId(), chapter.getDescription());
    }
}
