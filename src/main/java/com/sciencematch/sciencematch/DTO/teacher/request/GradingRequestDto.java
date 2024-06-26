package com.sciencematch.sciencematch.DTO.teacher.request;

import lombok.Data;

@Data
public class GradingRequestDto {

    private Long answerId;
    private Boolean rightAnswer;

}
