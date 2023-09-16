package com.sciencematch.sciencematch.domain.paper_test;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaperTestQuestion extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_question_id")
    private Long id;

    private String question;
    private String solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_test_id")
    private PaperTest paperTest;

    @Builder
    public PaperTestQuestion(String question, String solution) {
        this.question = question;
        this.solution = solution;
    }

    public void setPaperTest(PaperTest paperTest) {
        this.paperTest = paperTest;
    }
}
