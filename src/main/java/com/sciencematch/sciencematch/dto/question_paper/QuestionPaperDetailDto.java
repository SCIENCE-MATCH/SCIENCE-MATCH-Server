package com.sciencematch.sciencematch.dto.question_paper;

import com.sciencematch.sciencematch.enums.Category;
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
