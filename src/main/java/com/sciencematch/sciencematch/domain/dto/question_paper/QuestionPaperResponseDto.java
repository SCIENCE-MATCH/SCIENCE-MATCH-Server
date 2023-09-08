package com.sciencematch.sciencematch.domain.dto.question_paper;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.domain.QuestionPaper;
import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import lombok.Data;

@Data
public class QuestionPaperResponseDto {

    private Long id;
    private School school;
    private Level level;
    private Integer questionNum;
    private String title;
    private String makerName;
    private Category category;
    private Subject subject;

    @QueryProjection
    public QuestionPaperResponseDto(Long id, School school, Level level, Integer questionNum,
        String title, String makerName, Category category, Subject subject) {
        this.id = id;
        this.school = school;
        this.level = level;
        this.questionNum = questionNum;
        this.title = title;
        this.makerName = makerName;
        this.category = category;
        this.subject = subject;
    }

    public static QuestionPaperResponseDto of(QuestionPaper questionPaper) {
        return new QuestionPaperResponseDto(questionPaper.getId(), questionPaper.getSchool(),
            questionPaper.getLevel(), questionPaper.getQuestionNum(),
            questionPaper.getTitle(), questionPaper.getMakerName(), questionPaper.getCategory(), questionPaper.getSubject());
    }

}
