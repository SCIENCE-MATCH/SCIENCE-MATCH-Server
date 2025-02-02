package com.sciencematch.sciencematch.dto.student;

import com.sciencematch.sciencematch.enums.AssignStatus;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignQuestionPaperResponseDto {

    private Long id;
    private Long originQuestionPaperId;
    private Subject subject;
    private AssignStatus assignStatus;
    private String title;
    private Integer score;
    private Integer totalScore;
    private Integer questionNum;

    public static AssignQuestionPaperResponseDto of(AssignQuestions assignQuestions) {
        return new AssignQuestionPaperResponseDto(assignQuestions.getId(),
            assignQuestions.getQuestionPaper().getId(), assignQuestions.getSubject(), assignQuestions.getAssignStatus(),
            assignQuestions.getQuestionPaper().getTitle(), assignQuestions.getScore(),
            assignQuestions.getTotalScore(), assignQuestions.getQuestionNum());
    }


}
