package com.sciencematch.sciencematch.DTO.teacher.response;

import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryPTResponseDto {

    private Long id;
    private String question;

    public static SummaryPTResponseDto of(AssignPaperTest assignPaperTest) {
        return new SummaryPTResponseDto(assignPaperTest.getId(),
            assignPaperTest.getPaperTest().getQuestion());
    }

}
