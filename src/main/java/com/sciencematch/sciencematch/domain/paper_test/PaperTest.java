package com.sciencematch.sciencematch.domain.paper_test;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
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
public class PaperTest extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_id")
    private Long id;

    //1대1 질문지 조회용 데이터
    private School school;
    private Semester semester;
    private Subject subject;
    private Long chapterId;

    private String title;
    private String makerName;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "paperTest")
    private final List<PaperTestQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "paperTest")
    private final List<AssignPaperTest> assignPaperTests = new ArrayList<>();

    @Builder
    public PaperTest(School school, Semester semester, Subject subject, Long chapterId, String title, String makerName) {
        this.school = school;
        this.semester = semester;
        this.subject = subject;
        this.chapterId = chapterId;
        this.title = title;
        this.makerName = makerName;
    }

    public void addQuestions(PaperTestQuestion paperTestQuestion) {
        this.questions.add(paperTestQuestion);
        paperTestQuestion.setPaperTest(this);
    }
}
