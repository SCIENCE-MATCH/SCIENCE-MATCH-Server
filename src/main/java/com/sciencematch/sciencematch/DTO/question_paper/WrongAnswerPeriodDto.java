package com.sciencematch.sciencematch.DTO.question_paper;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WrongAnswerPeriodDto {

    private LocalDateTime start;
    private LocalDateTime end;

}
