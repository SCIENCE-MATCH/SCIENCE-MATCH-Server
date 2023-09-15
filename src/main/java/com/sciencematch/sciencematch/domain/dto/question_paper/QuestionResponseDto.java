package com.sciencematch.sciencematch.domain.dto.question_paper;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import lombok.Data;

@Data
public class QuestionResponseDto {

    private Long questionId;
    private String imageURL;
    private Category category;
    private Level level;

    @QueryProjection
    public QuestionResponseDto(Long questionId, String imageURL, Category category, Level level) {
        this.questionId = questionId;
        this.imageURL = imageURL;
        this.category = category;
        this.level = level;
    }

    public static QuestionResponseDto of(Question question) {
        return new QuestionResponseDto(question.getId(), question.getImage(),
            question.getCategory(), question.getLevel());
    }
}
