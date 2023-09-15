package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.controller.dto.request.StudentRequestDto;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.domain.enumerate.Authority;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
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
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE student_id=?")
public class Student extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;
    @Schema(description = "고1")
    private String grade;
    @Schema(description = "김사메")
    private String name;
    private String parentNum;
    @Column(unique = true)
    private String phoneNum;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private final List<TeamStudent> teamStudents = new ArrayList<>();
    private Authority authority;

    private final Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private final List<AssignQuestions> assignQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private final List<AssignPaperTest> assignPaperTests = new ArrayList<>();

    @Builder
    public Student(String grade, String name, String parentNum, String phoneNum,
        Authority authority, Teacher teacher) {
        this.grade = grade;
        this.name = name;
        this.parentNum = parentNum;
        this.phoneNum = phoneNum;
        this.authority = authority;
        changeTeacher(teacher);
    }

    public Student changeInfo(StudentRequestDto studentRequestDto) {
        this.grade = studentRequestDto.getGrade();
        this.name = studentRequestDto.getName();
        this.parentNum = studentRequestDto.getParentNum();
        this.phoneNum = studentRequestDto.getPhoneNum();
        return this;
    }

    private void changeTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.getStudents().add(this);
    }
}
