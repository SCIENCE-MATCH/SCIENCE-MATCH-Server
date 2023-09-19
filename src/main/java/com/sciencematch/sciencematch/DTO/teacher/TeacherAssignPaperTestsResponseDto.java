package com.sciencematch.sciencematch.DTO.teacher;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherAssignPaperTestsResponseDto {

    private Long id;
    private School school;
    private Semester semester;
    private Subject subject;
    private String title;
    private AssignStatus assignStatus;
    private LocalDateTime createdAt;

    public static TeacherAssignPaperTestsResponseDto of(AssignPaperTest assignPaperTest) {
        return new TeacherAssignPaperTestsResponseDto(assignPaperTest.getId(),
            assignPaperTest.getPaperTest()
                .getSchool(), assignPaperTest.getPaperTest().getSemester(),
            assignPaperTest.getSubject(), assignPaperTest.getPaperTest().getTitle(),
            assignPaperTest.getAssignStatus(), assignPaperTest.getCreateAt());
    }


}
