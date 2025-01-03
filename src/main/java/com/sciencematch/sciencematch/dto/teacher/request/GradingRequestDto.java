package com.sciencematch.sciencematch.dto.teacher.request;

import lombok.Data;

@Data
public class GradingRequestDto {

    private Long answerId;
    private Boolean rightAnswer;

}
