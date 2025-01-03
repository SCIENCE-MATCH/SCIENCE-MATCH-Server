package com.sciencematch.sciencematch.domain.paper_test;

import com.sciencematch.sciencematch.enums.AssignStatus;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignPaperTest extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "assign_paper_test_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_test_id")
    private PaperTest paperTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private Subject subject;
    private AssignStatus assignStatus;
    private String teacherName;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private PaperTestAnswer paperTestAnswer;

    private void setStudent(Student student) {
        this.student = student;
        if (!student.getAssignPaperTests().contains(this)) {
            student.getAssignPaperTests().add(this);
        }
    }

    private void setPaperTest(PaperTest paperTest) {
        this.paperTest = paperTest;
        if (!paperTest.getAssignPaperTests().contains(this)) {
            paperTest.getAssignPaperTests().add(this);
        }
    }

    public void setPaperTestAnswerAndAssignStatus(PaperTestAnswer paperTestAnswer) {
        paperTestAnswer.setAssignPaperTest(this);
        this.paperTestAnswer = paperTestAnswer;
        this.assignStatus = AssignStatus.COMPLETE;
    }

    @Builder
    public AssignPaperTest(Student student, PaperTest paperTest, Subject subject, String teacherName) {
        setStudent(student);
        setPaperTest(paperTest);
        this.subject = subject;
        this.assignStatus = AssignStatus.WAITING;
        this.teacherName = teacherName;
    }

}
