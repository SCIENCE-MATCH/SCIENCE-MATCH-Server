package com.sciencematch.sciencematch.domain.question;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//학생에게 할당한 문제
public class AssignQuestions extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "assign_questions_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_paper_id")
    private QuestionPaper questionPaper;

    private Subject subject;
    private AssignStatus assignStatus;

    private Integer totalScore;
    private Integer score;
    private Integer questionNum;

    @OneToMany(mappedBy = "assignQuestions")
    private List<Answer> answer;

    private void setStudent(Student student) {
        this.student = student;
        if (!student.getAssignQuestions().contains(this)) {
            student.getAssignQuestions().add(this);
        }
    }

    private void setQuestionPaper(QuestionPaper questionPaper) {
        this.questionPaper = questionPaper;
        if (!questionPaper.getAssignQuestions().contains(this)) {
            questionPaper.getAssignQuestions().add(this);
        }
    }

    public void setAnswerAndAssignStatus(List<Answer> answers) {
        for (Answer answer : answers) {
            answer.setAssignQuestions(this);
        }
        this.answer = answers;
        this.assignStatus = AssignStatus.COMPLETE;
    }

    public void setGraded() {
        if (this.assignStatus != AssignStatus.GRADED) {
            this.assignStatus = AssignStatus.GRADED;
        }
    }

    public void plusScore(Integer score) {
        this.score += score;
    }

    public void plusTotalScore(Integer score) {
        this.totalScore += score;
    }

    @Builder
    public AssignQuestions(Student student, QuestionPaper questionPaper, Subject subject,
        AssignStatus assignStatus) {
        setStudent(student);
        setQuestionPaper(questionPaper);
        this.subject = subject;
        this.assignStatus = assignStatus;
        this.questionNum = questionPaper.getQuestionNum();
        this.totalScore = 0;
        this.score = 0;
    }
}
