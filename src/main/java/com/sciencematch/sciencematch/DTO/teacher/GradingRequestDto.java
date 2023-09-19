package com.sciencematch.sciencematch.DTO.teacher;

import lombok.Data;

@Data
public class GradingRequestDto {

    private Long answerId;
    private Boolean rightAnswer;

}
