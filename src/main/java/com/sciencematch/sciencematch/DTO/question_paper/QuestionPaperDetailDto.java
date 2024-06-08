package com.sciencematch.sciencematch.DTO.question_paper;

import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPaperDetailDto {

    private String pdf;
    private List<Category> categories;

    public static QuestionPaperDetailDto of(AssignQuestions assignQuestions) {
        String pdf = assignQuestions.getQuestionPaper().getPdf();
        List<Category> categories = assignQuestions.getAnswer().stream().map(Answer::getCategory).collect(
            Collectors.toList());
        return new QuestionPaperDetailDto(pdf, categories);

    }

}
