package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import java.util.ArrayList;
import java.util.List;
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
    private Category category;
    private QuestionTag questionTag;
    private String title;
    private String makerName;
    private Subject subject;
    private String themeColor;
    private Integer template;

    @OneToMany(mappedBy = "questionPaper")
    private final List<ConnectQuestion> connectQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "questionPaper")
    private final List<AssignQuestions> assignQuestions = new ArrayList<>();

    @Builder
    public QuestionPaper(Integer questionNum, School school, Category category, QuestionTag questionTag,
        String title, String makerName, Subject subject, String themeColor, Integer template) {
        this.questionNum = questionNum;
        this.school = school;
        this.category = category;
        this.questionTag = questionTag;
        this.title = title;
        this.makerName = makerName;
        this.subject = subject;
        this.themeColor = themeColor;
        this.template = template;
    }


}
