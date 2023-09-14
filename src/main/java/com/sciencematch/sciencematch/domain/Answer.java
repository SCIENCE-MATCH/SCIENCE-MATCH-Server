package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.Category;
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
public class Answer {

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String submitAnswer;
    private String solutionImg;

    private Category category;

    private Long chapterId;
    private Boolean rightAnswer;

    @ManyToOne
    @JoinColumn(name = "assign_questions_id")
    private AssignQuestions assignQuestions;

    @Builder
    public Answer(String submitAnswer, String solutionImg, Category category, Long chapterId) {
        this.submitAnswer = submitAnswer;
        this.solutionImg = solutionImg;
        this.category = category;
        this.chapterId = chapterId;
        this.rightAnswer = false;
    }

}
