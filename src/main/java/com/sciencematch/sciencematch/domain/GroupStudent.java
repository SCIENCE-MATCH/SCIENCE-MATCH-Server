package com.sciencematch.sciencematch.domain;


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
public class GroupStudent {

    @Id
    @GeneratedValue
    @Column(name = "group_student_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // 연관 관계 편의 메서드
    public void setGroup(Group group) {
        this.group = group;
        if (!group.getGroupStudents().contains(this)) {
            group.getGroupStudents().add(this);
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        if (!student.getGroupStudents().contains(this)) {
            student.getGroupStudents().add(this);
        }
    }

    @Builder
    public GroupStudent(Group group, Student student) {
        setGroup(group);
        setStudent(student);
    }
}
