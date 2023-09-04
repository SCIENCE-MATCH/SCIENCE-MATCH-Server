package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String image;

    private Level level;

    private Category category;

    private String answer;

    private String solution;

    private String bookName;

    private Integer page;

    @Builder
    private Question(String image, Level level, Category category, String answer, String solution,
        String bookName, Integer page) {
        this.image = image;
        this.level = level;
        this.category = category;
        this.answer = answer;
        this.solution = solution;
        this.bookName = bookName;
        this.page = page;
    }

}
