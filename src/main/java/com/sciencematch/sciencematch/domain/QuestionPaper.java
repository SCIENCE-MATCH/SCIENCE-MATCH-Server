package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import com.sciencematch.sciencematch.domain.enumerate.QuestionTag;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private Level level;
    private Category category;
    private QuestionTag questionTag;
    private String title;
    private String makerName;
    private Subject subject;

    @OneToMany(mappedBy = "questionPaper", fetch = FetchType.LAZY)
    private final List<ConnectQuestion> connectQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "questionPaper", fetch = FetchType.LAZY)
    private final List<AssignQuestions> assignQuestions = new ArrayList<>();

    @Builder
    public QuestionPaper(School school, Level level, Category category, QuestionTag questionTag,
        String title, String makerName, Subject subject) {
        this.questionNum = 0;
        this.school = school;
        this.level = level;
        this.category = category;
        this.questionTag = questionTag;
        this.title = title;
        this.makerName = makerName;
        this.subject = subject;
    }

    public void plusQuestionNum() {
        this.questionNum += 1;
    }



}
