package com.sciencematch.sciencematch.dto.teacher.response;

import com.sciencematch.sciencematch.enums.AssignStatus;
import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryAQResponseDto {

    private Long id;
    private Category category;
    private String title;
    private Subject subject;
    private Integer questionNum;
    private Integer correctPercent;
    private AssignStatus assignStatus;


    public static SummaryAQResponseDto of(AssignQuestions assignQuestions) {
        Integer correctPercent = assignQuestions.getTotalScore() == 0 ? 0 : (int) (((double) assignQuestions.getScore() / assignQuestions.getTotalScore()) * 100);
        return new SummaryAQResponseDto(assignQuestions.getId(), assignQuestions.getQuestionPaper().getCategory(),
            assignQuestions.getQuestionPaper().getTitle(), assignQuestions.getSubject(),
            assignQuestions.getQuestionNum(), correctPercent, assignQuestions.getAssignStatus());
    }

}
