package com.sciencematch.sciencematch.dto.student;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SolvedQuestionPaperDto {
    private Integer score;
    private Integer totalScore;
    private Integer correctNum;
    private Integer questionNum;
    private LocalDateTime createdAt;
    private List<AnswerResponseDto> answerResponseDtos;
}
