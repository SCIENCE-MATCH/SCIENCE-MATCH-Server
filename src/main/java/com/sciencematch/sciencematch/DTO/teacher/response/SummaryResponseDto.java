package com.sciencematch.sciencematch.DTO.teacher.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryResponseDto {

    private Integer assignQuestionTotalNum;
    private Integer assignQuestionAverageScore;
    private Integer assignPaperTotalNum;
    private Integer assignPaperCorrectPercent;
    private List<SummaryAQResponseDto> solvedAQDto;
    private List<SummaryAQResponseDto> notSolvedAQDto;
    private List<SummaryPTResponseDto> solvedPTDto;
}
