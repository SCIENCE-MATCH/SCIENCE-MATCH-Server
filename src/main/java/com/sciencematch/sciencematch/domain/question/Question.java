package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private School school;
    private Semester semester;
    private Subject subject;
    private String image;
    private Level level;
    private Category category;
    private String answer;
    private String solution;
    private String bookName;
    private Integer page;
    private QuestionTag questionTag;

    private Long chapterId;

    private final Boolean deleted = false;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private final List<ConnectQuestion> connectQuestions = new ArrayList<>();

    @Builder
    private Question(School school, Semester semester, Subject subject, String image, Level level,
        Category category, String answer, String solution,
        String bookName, Integer page, QuestionTag questionTag, Long chapterId) {
        this.school = school;
        this.semester = semester;
        this.subject = subject;
        this.image = image;
        this.level = level;
        this.category = category;
        this.answer = answer;
        this.solution = solution;
        this.bookName = bookName;
        this.page = page;
        this.questionTag = questionTag;
        this.chapterId = chapterId;
    }

}
