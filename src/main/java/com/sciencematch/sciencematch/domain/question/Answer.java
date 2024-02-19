package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.Enums.Category;
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
public class Answer {

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String submitAnswer;
    private String solution;
    private String solutionImg;

    private Category category;

    private Long chapterId;
    private Boolean rightAnswer;
    private Boolean graded;
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assign_questions_id")
    private AssignQuestions assignQuestions;

    @Builder
    public Answer(String submitAnswer, String solution, String solutionImg, Category category, Long chapterId, Integer score) {
        this.submitAnswer = submitAnswer;
        this.solution = solution;
        this.solutionImg = solutionImg;
        this.category = category;
        this.chapterId = chapterId;
        this.rightAnswer = false;
        this.graded = false;
        this.score = score;
    }

    public void setAssignQuestions(AssignQuestions assignQuestions) {
        this.assignQuestions = assignQuestions;
    }

    public void setSubmitAnswer(String submitAnswer) {
        this.submitAnswer = submitAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
        this.graded = true;
    }

}
