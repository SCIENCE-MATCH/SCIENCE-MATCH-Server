package com.sciencematch.sciencematch.dto.teacher.response;

import com.sciencematch.sciencematch.enums.AssignStatus;
import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.enums.QuestionTag;
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
    private String pdf;
    private Level level;
    private String boundary;
    private Long originQuestionPaperId;
    private QuestionTag questionTag;
    
    public static TeacherAssignQuestionsResponseDto of(AssignQuestions assignQuestions, String boundary) {
        System.out.println("== qp.getQuestionTag(): " + assignQuestions.getQuestionPaper().getQuestionTag());
        return new TeacherAssignQuestionsResponseDto(assignQuestions.getId(),
            assignQuestions.getQuestionPaper().getSchool(), assignQuestions.getQuestionPaper()
            .getCategory(), assignQuestions.getQuestionPaper().getTitle(),
            assignQuestions.getSubject(), assignQuestions.getAssignStatus(),
            assignQuestions.getQuestionPaper().getQuestionNum(), assignQuestions.getCreateAt(), assignQuestions.getQuestionPaper().getPdf(),
            assignQuestions.getQuestionPaper().getLevel(), boundary, assignQuestions.getQuestionPaper().getId(), assignQuestions.getQuestionPaper().getQuestionTag());
    }


}
