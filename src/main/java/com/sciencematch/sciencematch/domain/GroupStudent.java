package com.sciencematch.sciencematch.domain;


import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
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
public class GroupStudent extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "group_student_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // 연관 관계 편의 메서드
    public void setGroups(Groups groups) {
        this.groups = groups;
        if (!groups.getGroupStudents().contains(this)) {
            groups.getGroupStudents().add(this);
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        if (!student.getGroupStudents().contains(this)) {
            student.getGroupStudents().add(this);
        }
    }

    @Builder
    public GroupStudent(Groups groups, Student student) {
        setGroups(groups);
        setStudent(student);
    }
}
