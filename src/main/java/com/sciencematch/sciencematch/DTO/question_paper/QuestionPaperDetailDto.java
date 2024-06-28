package com.sciencematch.sciencematch.DTO.question_paper;

import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPaperDetailDto {

    private String pdf;
    private List<Category> categories;

    public static QuestionPaperDetailDto of(AssignQuestions assignQuestions, List<Category> categories) {
        String pdf = assignQuestions.getQuestionPaper().getPdf();
        return new QuestionPaperDetailDto(pdf, categories);
    }

}
