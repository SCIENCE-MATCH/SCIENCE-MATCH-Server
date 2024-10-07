package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Csat extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "csat_id")
    private Long id;
    private Integer year;
    private Integer month;
    private Subject subject;
    private Integer subjectNum;
    private String publisher;

}
