package com.sciencematch.sciencematch.DTO.teacher;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherAssignPaperTestsResponseDto {

    private Long id;
    private String chapterDescription;
    private String question;
    private AssignStatus assignStatus;
    private LocalDateTime createdAt;

    public static TeacherAssignPaperTestsResponseDto of(AssignPaperTest assignPaperTest) {
        return new TeacherAssignPaperTestsResponseDto(assignPaperTest.getId(),
            assignPaperTest.getPaperTest().getChapterDescription(), assignPaperTest.getPaperTest().getQuestion(),
            assignPaperTest.getAssignStatus(), assignPaperTest.getCreateAt());
    }


}
