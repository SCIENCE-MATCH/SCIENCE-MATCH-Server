package com.sciencematch.sciencematch.dto.book.response;

import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookQuestionResponseDto {

    private Long questionId;
    private Double pageOrder;
    private String questionImg;
    private Category category;
    private Level level;
    private Integer score;
    private Long chapterId;
    private String chapterDescription;

    public static BookQuestionResponseDto of(Question question, Chapter chapter) {
        return new BookQuestionResponseDto(question.getId(), question.getPageOrder(),
            question.getImage(), question.getCategory(), question.getLevel(), question.getScore(),
            chapter.getId(), chapter.getDescription());
    }
}
