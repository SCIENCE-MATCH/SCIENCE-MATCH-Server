package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.AssignStatus;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
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
//학생에게 할당한 문제
public class AssignQuestions {

    @Id
    @GeneratedValue
    @Column(name = "assign_questions_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "question_paper_id")
    private QuestionPaper questionPaper;

    private Subject subject;
    private AssignStatus assignStatus;

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

    @Builder
    public AssignQuestions(Student student, QuestionPaper questionPaper, Subject subject) {
        setStudent(student);
        setQuestionPaper(questionPaper);
        this.subject = subject;
        this.assignStatus = AssignStatus.WAITING;
    }
}
