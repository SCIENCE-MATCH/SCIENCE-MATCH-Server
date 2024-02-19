package com.sciencematch.sciencematch.DTO.student;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolvedPaperTestDto {
    private Integer score;
    private Integer totalScore;
    private Integer correctNum;
    private Integer questionNum;
    private List<AnswerResponseDto> answerResponseDtos;
}
