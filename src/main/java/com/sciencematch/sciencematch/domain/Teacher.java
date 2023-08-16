package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.Authority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    private String email;
    private String password;
    private String name;

    private String academy;

    private String logo;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNum;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private final Boolean deleted = Boolean.FALSE;

    @Builder
    public Teacher(String name, String email, String password, String phoneNum,
        Authority authority) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.authority = authority;
    }

    public void changeLogo(String logoURL) {
        this.logo = logoURL;
    }
}
