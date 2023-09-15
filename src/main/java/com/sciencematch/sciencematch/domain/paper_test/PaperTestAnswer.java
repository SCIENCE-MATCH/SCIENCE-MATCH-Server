package com.sciencematch.sciencematch.domain.paper_test;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class PaperTestAnswer {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_answer_id")
    private Long id;

    private String submitAnswer;
    private Boolean rightAnswer;

    @ManyToOne
    @JoinColumn(name = "assign_paper_test_id")
    private AssignPaperTest assignPaperTest;

    @Builder
    public PaperTestAnswer(String submitAnswer, Boolean rightAnswer, AssignPaperTest assignPaperTest) {
        this.submitAnswer = submitAnswer;
        this.rightAnswer = rightAnswer;
        this.assignPaperTest = assignPaperTest;
    }

}
