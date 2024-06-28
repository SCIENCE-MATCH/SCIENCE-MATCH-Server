package com.sciencematch.sciencematch.DTO.book.response;

import com.sciencematch.sciencematch.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookQuestionResponseDto {

    private Long questionId;
    private Double pageOrder;

    public static BookQuestionResponseDto of(Question question) {
        return new BookQuestionResponseDto(question.getId(), question.getPageOrder());
    }
}
