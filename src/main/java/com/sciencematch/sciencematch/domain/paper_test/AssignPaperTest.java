package com.sciencematch.sciencematch.domain.paper_test;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
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
public class AssignPaperTest extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "assign_paper_test_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paper_test_id")
    private PaperTest paperTest;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private Subject subject;
    private AssignStatus assignStatus;

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

    @Builder
    public AssignPaperTest(Student student, PaperTest paperTest, Subject subject) {
        setStudent(student);
        setPaperTest(paperTest);
        this.subject = subject;
        this.assignStatus = AssignStatus.WAITING;
    }

}
