package com.sciencematch.sciencematch.DTO.question_paper;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuestionPaperResponseDto {

    private Long id;
    private School school;
    private Semester semester;
    private Integer questionNum;
    private String title;
    private String makerName;
    private Category category;
    private QuestionTag questionTag;
    private Subject subject;
    private Level level;
    private String pdf;
    private LocalDateTime createdAt;

    @QueryProjection
    public QuestionPaperResponseDto(Long id, School school, Semester semester, Integer questionNum,
        String title, String makerName, Category category, QuestionTag questionTag, Subject subject, Level level,
        String pdf, LocalDateTime createdAt) {
        this.id = id;
        this.school = school;
        this.semester = semester;
        this.questionNum = questionNum;
        this.title = title;
        this.makerName = makerName;
        this.category = category;
        this.questionTag = questionTag;
        this.subject = subject;
        this.level = level;
        this.pdf = pdf;
        this.createdAt = createdAt;
    }

    public static QuestionPaperResponseDto of(QuestionPaper questionPaper) {
        return new QuestionPaperResponseDto(questionPaper.getId(), questionPaper.getSchool(), questionPaper.getSemester(), questionPaper.getQuestionNum(),
            questionPaper.getTitle(), questionPaper.getMakerName(), questionPaper.getCategory(), questionPaper.getQuestionTag(),
            questionPaper.getSubject(), questionPaper.getLevel(), questionPaper.getPdf(), questionPaper.getCreateAt());
    }

}
