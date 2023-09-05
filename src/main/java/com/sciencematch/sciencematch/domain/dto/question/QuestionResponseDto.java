package com.sciencematch.sciencematch.domain.dto.question;

import com.sciencematch.sciencematch.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionResponseDto {

    private Long questionId;

    private String imageURL;

    public static QuestionResponseDto of(Question question) {
        return new QuestionResponseDto(question.getId(), question.getImage());
    }

}
