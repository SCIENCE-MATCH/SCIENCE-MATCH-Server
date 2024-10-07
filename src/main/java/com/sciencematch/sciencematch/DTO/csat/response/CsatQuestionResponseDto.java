package com.sciencematch.sciencematch.DTO.csat.response;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.question.Question;
import lombok.Data;

@Data
public class CsatQuestionResponseDto {

    private Long questionId;
    private Double pageOrder;
    private String questionImg;
    private Category category;
    private Level level;
    private Integer score;
    private Long chapterId;
    private String chapterDescription;

    public static CsatQuestionResponseDto of(Question question, Chapter chapter) {
        return new CsatQuestionResponseDto(question.getId(), question.getPageOrder(),
            question.getImage(), question.getCategory(), question.getLevel(), question.getScore(),
            chapter.getId(), chapter.getDescription());
    }

    @QueryProjection
    public CsatQuestionResponseDto(Long questionId, Double pageOrder, String questionImg, Category category, Level level, Integer score, Long chapterId, String chapterDescription) {
        this.questionId = questionId;
        this.pageOrder = pageOrder;
        this.questionImg = questionImg;
        this.category = category;
        this.level = level;
        this.score = score;
        this.chapterId = chapterId;
        this.chapterDescription = chapterDescription;
    }
}
