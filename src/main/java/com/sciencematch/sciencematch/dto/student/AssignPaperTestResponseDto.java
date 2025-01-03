package com.sciencematch.sciencematch.dto.student;

import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignPaperTestResponseDto {

    private Long id;
    private Subject subject;
    private String question;
    private String image;

    public static AssignPaperTestResponseDto of(AssignPaperTest assignPaperTest) {
        return new AssignPaperTestResponseDto(assignPaperTest.getId(),
            assignPaperTest.getSubject(),
            assignPaperTest.getPaperTest().getQuestion(),
            assignPaperTest.getPaperTest().getImage());
    }
}
