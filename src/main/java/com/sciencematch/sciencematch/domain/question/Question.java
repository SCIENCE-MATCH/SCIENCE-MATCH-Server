package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.enums.QuestionTag;
import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Semester;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
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
public class Question extends AuditingTimeEntity {

    private final Boolean deleted = false;
    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private final List<ConnectQuestion> connectQuestions = new ArrayList<>();
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
    private String solution;
    private String solutionImg;
    private String bookName;
    private Integer page;
    private Double pageOrder;
    private QuestionTag questionTag;
    private Long chapterId;
    private Long bookId;
    private Long csatId;
    private Integer score;

    @Builder
    private Question(School school, Semester semester, Subject subject, String image, Level level,
        Category category, String solution, String solutionImg, String bookName, Integer page,
        Double pageOrder, QuestionTag questionTag, Long chapterId, Long bookId, Long csatId,
        Integer score) {
        this.school = school;
        this.semester = semester;
        this.subject = subject;
        this.image = image;
        this.level = level;
        this.category = category;
        this.solution = solution;
        this.solutionImg = solutionImg;
        this.bookName = bookName;
        this.page = page;
        this.pageOrder = pageOrder;
        this.questionTag = questionTag;
        this.chapterId = chapterId;
        this.bookId = bookId;
        this.csatId = csatId;
        this.score = score;

    }

}
