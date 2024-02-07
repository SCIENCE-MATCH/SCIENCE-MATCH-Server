package com.sciencematch.sciencematch.domain.paper_test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaperTestAnswer {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_answer_id")
    private Long id;

    private String submitAnswer;
    private String solution;
    private Boolean rightAnswer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_paper_test_id")
    private AssignPaperTest assignPaperTest;

    public void setAssignPaperTest(AssignPaperTest assignPaperTest) {
        this.assignPaperTest = assignPaperTest;
    }

    public void setSubmitAnswer(String submitAnswer) {
        this.submitAnswer = submitAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Builder
    public PaperTestAnswer(String solution) {
        this.solution = solution;
        this.rightAnswer = false;
    }

}
