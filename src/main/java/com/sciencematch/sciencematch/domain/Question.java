package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE question_id=?")
@Where(clause = "deleted=false")
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

    private final Boolean deleted = false;

    @OneToOne
    private Chapter chapter;

    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY)
    private final List<ConnectQuestion> connectQuestions = new ArrayList<>();

    @Builder
    private Question(String image, Level level, Category category, String answer, String solution,
        String bookName, Integer page, Chapter chapter) {
        this.image = image;
        this.level = level;
        this.category = category;
        this.answer = answer;
        this.solution = solution;
        this.bookName = bookName;
        this.page = page;
        this.chapter = chapter;
    }

}
