package com.sciencematch.sciencematch.DTO.student;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignPaperTestResponseDto {

    private Long id;
    private Subject subject;
    private AssignStatus assignStatus;
    private String title;

    public static AssignPaperTestResponseDto of(AssignPaperTest assignPaperTest) {
        return new AssignPaperTestResponseDto(assignPaperTest.getId(),
            assignPaperTest.getSubject(), assignPaperTest.getAssignStatus(),
            assignPaperTest.getPaperTest().getTitle());
    }
}
