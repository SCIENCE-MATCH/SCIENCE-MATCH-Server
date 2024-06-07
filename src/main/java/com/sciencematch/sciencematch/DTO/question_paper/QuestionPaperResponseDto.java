package com.sciencematch.sciencematch.DTO.question_paper;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuestionPaperResponseDto {

    private Long id;
    private School school;
    private Integer questionNum;
    private String title;
    private String makerName;
    private Category category;
    private Subject subject;
    private String pdf;
    private LocalDateTime createdAt;

    @QueryProjection
    public QuestionPaperResponseDto(Long id, School school, Integer questionNum,
        String title, String makerName, Category category, Subject subject,
        String pdf, LocalDateTime createdAt) {
        this.id = id;
        this.school = school;
        this.questionNum = questionNum;
        this.title = title;
        this.makerName = makerName;
        this.category = category;
        this.subject = subject;
        this.pdf = pdf;
        this.createdAt = createdAt;
    }

    public static QuestionPaperResponseDto of(QuestionPaper questionPaper) {
        return new QuestionPaperResponseDto(questionPaper.getId(), questionPaper.getSchool(), questionPaper.getQuestionNum(),
            questionPaper.getTitle(), questionPaper.getMakerName(), questionPaper.getCategory(), questionPaper.getSubject(),
            questionPaper.getPdf(), questionPaper.getCreateAt());
    }

}
