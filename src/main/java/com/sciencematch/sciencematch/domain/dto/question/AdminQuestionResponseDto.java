package com.sciencematch.sciencematch.domain.dto.question;

import com.sciencematch.sciencematch.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminQuestionResponseDto {

    private Long questionId;

    private String imageURL;

    public static AdminQuestionResponseDto of(Question question) {
        return new AdminQuestionResponseDto(question.getId(), question.getImage());
    }

}
