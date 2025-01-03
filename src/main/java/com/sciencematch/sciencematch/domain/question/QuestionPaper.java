package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.enums.Semester;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.QuestionTag;
import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Subject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionPaper extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "question_paper_id")
    private Long id;

    private Integer questionNum;
    private School school;
    private Semester semester;
    private Category category;
    private QuestionTag questionTag;
    private String title;
    private String makerName;
    private Subject subject;
    private Level level;
    private String themeColor;
    private Integer template;
    private Long minChapterId;
    private Long maxChapterId;

    private String pdf;

    @OneToMany(mappedBy = "questionPaper", cascade = {CascadeType.REMOVE})
    private final List<ConnectQuestion> connectQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "questionPaper", cascade = {CascadeType.REMOVE})
    private final List<AssignQuestions> assignQuestions = new ArrayList<>();

    @Builder
    public QuestionPaper(Integer questionNum, School school, Semester semester, Category category, QuestionTag questionTag,
        String title, String makerName, Subject subject, Level level, String themeColor, Integer template,
        Long minChapterId, Long maxChapterId, String pdf) {
        this.questionNum = questionNum;
        this.school = school;
        this.semester = semester;
        this.category = category;
        this.questionTag = questionTag;
        this.title = title;
        this.makerName = makerName;
        this.subject = subject;
        this.level = level;
        this.themeColor = themeColor;
        this.template = template;
        this.pdf = pdf;
        this.minChapterId = minChapterId;
        this.maxChapterId = maxChapterId;
    }


}
