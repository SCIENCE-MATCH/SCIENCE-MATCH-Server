package com.sciencematch.sciencematch.DTO.question_paper;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.question.Question;
import lombok.Data;

@Data
public class QuestionResponseDto {

    private Long questionId;
    private String imageURL;
    private Category category;
    private Level level;
    private Integer score;
    private Long chapterId;
    private String description;

    @QueryProjection
    public QuestionResponseDto(Long questionId, String imageURL, Category category, Level level, Integer score, Long chapterId, String description) {
        this.questionId = questionId;
        this.imageURL = imageURL;
        this.category = category;
        this.level = level;
        this.score = score;
        this.chapterId = chapterId;
        this.description = description;
    }

    public static QuestionResponseDto of(Question question, Chapter chapter) {
        return new QuestionResponseDto(question.getId(), question.getImage(),
            question.getCategory(), question.getLevel(), question.getScore(), chapter.getId(), chapter.getDescription());
    }
}
