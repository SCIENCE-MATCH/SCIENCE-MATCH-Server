package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
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
public class PaperTestQuestion extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_question_id")
    private Long id;

    private String question;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "paper_test_id")
    private PaperTest paperTest;

    @Builder
    public PaperTestQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
