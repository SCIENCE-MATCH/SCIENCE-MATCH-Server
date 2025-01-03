package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.enums.AssignStatus;
import com.sciencematch.sciencematch.enums.Category;
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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    @Setter
    private String submitAnswer;
    private String solution;
    private String solutionImg;

    private Category category;

    private Long chapterId;
    private Boolean rightAnswer;
    private Boolean graded;
    private Integer score;

    private Long questionId;
    private String questionImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_questions_id")
    private AssignQuestions assignQuestions;

    @Builder
    public Answer(String submitAnswer, String solution, String solutionImg, Category category,
        Long chapterId, Integer score, Long questionId, String questionImg,
        AssignQuestions assignQuestions) {
        this.submitAnswer = submitAnswer;
        this.solution = solution;
        this.solutionImg = solutionImg;
        this.category = category;
        this.chapterId = chapterId;
        this.rightAnswer = false;
        this.graded = false;
        this.score = score;
        this.questionId = questionId;
        this.questionImg = questionImg;
        setAssignQuestions(assignQuestions);
    }

    public void setRightAnswer(Boolean rightAnswer) {
        if (assignQuestions.getAssignStatus() != AssignStatus.GRADED) {
            assignQuestions.setAssignStatus(AssignStatus.GRADED);
        }

        if (!graded) { // 처음 채점하는 경우
            this.graded = true;
            this.rightAnswer = rightAnswer;
            if (rightAnswer) {
                assignQuestions.setScore(true, score);
                return;
            }
        }

        if (this.rightAnswer != rightAnswer) { // 채점 결과가 달라지는 경우
            this.rightAnswer = rightAnswer;
            assignQuestions.setScore(rightAnswer, score);
        }

    }

    public void setAssignQuestions(AssignQuestions assignQuestions) {
        this.assignQuestions = assignQuestions;
        assignQuestions.getAnswer().add(this);
    }

}
