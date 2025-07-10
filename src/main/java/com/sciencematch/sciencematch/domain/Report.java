package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    private String title;
    //보고서 작성 대상 기간
    private String period;
    private String pdf;

    //만든이
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    //대상 학생
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Builder
    public Report(String title, String period, String pdf, Teacher teacher, Student student) {

        this.title = title;
        this.period = period;
        this.pdf = pdf;
        this.teacher = teacher;
        this.student = student;
    }
}
