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
public class ConnectQuestion extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "connect_question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "question_paper_id")
    private QuestionPaper questionPaper;

    // 연관 관계 편의 메서드
    private void setQuestion(Question question) {
        this.question = question;
        if (!question.getConnectQuestions().contains(this)) {
            question.getConnectQuestions().add(this);
        }
    }

    private void setQuestionPaper(QuestionPaper questionPaper) {
        this.questionPaper = questionPaper;
        if (!questionPaper.getConnectQuestions().contains(this)) {
            questionPaper.getConnectQuestions().add(this);
            questionPaper.plusQuestionNum();
        }
    }

    @Builder
    public ConnectQuestion(Question question, QuestionPaper questionPaper) {
        setQuestion(question);
        setQuestionPaper(questionPaper);
    }

}
