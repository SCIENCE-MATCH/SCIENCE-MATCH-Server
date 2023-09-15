package com.sciencematch.sciencematch.domain.dto.question_paper;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
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
    private LocalDateTime createdAt;

    @QueryProjection
    public QuestionPaperResponseDto(Long id, School school, Integer questionNum,
        String title, String makerName, Category category, Subject subject, LocalDateTime createdAt) {
        this.id = id;
        this.school = school;
        this.questionNum = questionNum;
        this.title = title;
        this.makerName = makerName;
        this.category = category;
        this.subject = subject;
        this.createdAt = createdAt;
    }

    public static QuestionPaperResponseDto of(QuestionPaper questionPaper) {
        return new QuestionPaperResponseDto(questionPaper.getId(), questionPaper.getSchool(), questionPaper.getQuestionNum(),
            questionPaper.getTitle(), questionPaper.getMakerName(), questionPaper.getCategory(), questionPaper.getSubject(), questionPaper.getCreateAt());
    }

}
