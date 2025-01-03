package com.sciencematch.sciencematch.dto.question_paper;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WrongAnswerPeriodDto {

    private LocalDateTime start;
    private LocalDateTime end;

}
