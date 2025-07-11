package com.sciencematch.sciencematch.dto.report;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.domain.Report;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponseDto {
    private Long id;
    private String title;
    private String period;
    private String pdf;
    private LocalDateTime createdAt;
    private String studentName;
    private Long studentId;

    @QueryProjection
    public ReportResponseDto(Long id, String title, String Period,
                             String pdf, LocalDateTime createdAt, String studentName, Long studentId) {
        this.id = id;
        this.title = title;
        this.period = Period;
        this.pdf = pdf;
        this.createdAt = createdAt;
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public static ReportResponseDto from(Report report) {
        return new ReportResponseDto(
                report.getId(),
                report.getTitle(),
                report.getPeriod(),
                report.getPdf(),
                report.getCreateAt(),
                report.getStudent().getName(),
                report.getStudent().getId()
        );
    }
}
