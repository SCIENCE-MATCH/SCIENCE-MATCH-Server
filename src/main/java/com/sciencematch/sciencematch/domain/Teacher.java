package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.enums.Authority;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SQLDelete(sql = "UPDATE teacher SET deleted = true WHERE teacher_id=?")
@Where(clause = "deleted=false")
public class Teacher extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    private String email;
    private String password;
    private String name;

    private String academy;

    private String logo;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private final List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private final List<Team> team = new ArrayList<>();

    private String phoneNum;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    public Teacher(String name, String email, String password, String phoneNum, Authority authority) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.authority = authority;
    }

    public void changeLogo(String logoURL) {
        this.logo = logoURL;
    }

    public void assignTeacher() {this.authority = Authority.ROLE_TEACHER;}

    public void changePW(String password) {
        this.password = password;
    }
}
