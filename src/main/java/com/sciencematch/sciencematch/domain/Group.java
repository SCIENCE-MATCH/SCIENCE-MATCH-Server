package com.sciencematch.sciencematch.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE group SET deleted = true WHERE group_id=?")
@Where(clause = "deleted=false")
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<GroupStudent> groupStudents = new ArrayList<>();

    private final Boolean deleted = Boolean.FALSE;

    //반 생성시 선택한 학생들의 id로 조회해 리스트로 넘겨서 그룹 생성
    @Builder
    public Group(String name, Teacher teacher, List<Student> students) {
        this.name = name;
        this.teacher = teacher;
        this.groupStudents = students.stream()
            .map(student -> setGroupStudents(this, student))
            .collect(Collectors.toList()); //groupstudent 생성 및 연관관계 세팅
    }

    private GroupStudent setGroupStudents(Group group, Student student) {
        return GroupStudent.builder()
            .student(student)
            .group(group)
            .build();
    }

}
