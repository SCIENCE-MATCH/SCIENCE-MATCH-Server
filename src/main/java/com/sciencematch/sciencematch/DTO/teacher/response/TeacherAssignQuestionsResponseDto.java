package com.sciencematch.sciencematch.DTO.teacher.response;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherAssignQuestionsResponseDto {

    private Long id;
    private School school;
    private Category category;
    private String title;
    private Subject subject;
    private AssignStatus assignStatus;
    private Integer questionNum;
    private LocalDateTime createdAt;

    public static TeacherAssignQuestionsResponseDto of(AssignQuestions assignQuestions) {
        return new TeacherAssignQuestionsResponseDto(assignQuestions.getId(),
            assignQuestions.getQuestionPaper().getSchool(), assignQuestions.getQuestionPaper()
            .getCategory(), assignQuestions.getQuestionPaper().getTitle(),
            assignQuestions.getSubject(), assignQuestions.getAssignStatus(),
            assignQuestions.getQuestionPaper().getQuestionNum(), assignQuestions.getCreateAt());
    }


}
