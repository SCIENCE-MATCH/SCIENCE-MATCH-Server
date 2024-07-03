package com.sciencematch.sciencematch.domain.paper_test;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaperTestAnswer extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_answer_id")
    private Long id;

    private String solution;
    @Setter
    private String submitAnswer;
    @Setter
    private Boolean rightAnswer;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_paper_test_id")
    private AssignPaperTest assignPaperTest;

    @Builder
    public PaperTestAnswer(String solution) {
        this.solution = solution;
        this.rightAnswer = false;
    }

}
