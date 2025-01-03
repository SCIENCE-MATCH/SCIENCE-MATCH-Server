package com.sciencematch.sciencematch.dto.teacher.response;

import com.sciencematch.sciencematch.enums.AssignStatus;
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
