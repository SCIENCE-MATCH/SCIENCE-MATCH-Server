package com.sciencematch.sciencematch.dto.question;

import com.sciencematch.sciencematch.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminQuestionResponseDto {

    private Long questionId;
    private String imageURL;
    private Double pageOrder;

    public static AdminQuestionResponseDto of(Question question) {
        return new AdminQuestionResponseDto(question.getId(), question.getImage(),
            question.getPageOrder());
    }

}
