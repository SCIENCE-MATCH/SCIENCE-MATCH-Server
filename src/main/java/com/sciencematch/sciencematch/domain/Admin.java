package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.enumerate.Authority;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Teacher> waitingTeacher;

    @Builder
    public Admin(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.waitingTeacher = new ArrayList<>();
    }

}