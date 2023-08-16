package com.sciencematch.sciencematch.domain;

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

    private String birth;
    private Authority authority;

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    public Student(String grade, String name, String parentNum, String phoneNum, String birth) {
        this.grade = grade;
        this.name = name;
        this.parentNum = parentNum;
        this.phoneNum = phoneNum;
        this.birth = birth;
        this.authority = Authority.ROLE_STUDENT;
    }
}
