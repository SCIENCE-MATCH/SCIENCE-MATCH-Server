package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.controller.dto.request.StudentRequestDto;
import com.sciencematch.sciencematch.domain.enumerate.Authority;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE student_id=?")
@Where(clause = "deleted=false")
public class Student {

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

    private Authority authority;

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    public Student(String grade, String name, String parentNum, String phoneNum, Authority authority) {
        this.grade = grade;
        this.name = name;
        this.parentNum = parentNum;
        this.phoneNum = phoneNum;
        this.authority = authority;
    }

    public Student changeInfo(StudentRequestDto studentRequestDto) {
        this.grade = studentRequestDto.getGrade();
        this.name = studentRequestDto.getName();
        this.parentNum = studentRequestDto.getParentNum();
        this.phoneNum = studentRequestDto.getPhoneNum();
        return this;
    }
}
