package com.sciencematch.sciencematch.dto.report;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.domain.Report;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponseDto {
    private Long id;
    private String title;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String pdf;
    private LocalDateTime createdAt;
    private String studentName;

    @QueryProjection
    public ReportResponseDto(Long id, String title, LocalDateTime dateFrom, LocalDateTime dateTo,
                             String pdf, LocalDateTime createdAt, String studentName) {
        this.id = id;
        this.title = title;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.pdf = pdf;
        this.createdAt = createdAt;
        this.studentName = studentName;
    }

    public static ReportResponseDto from(Report report) {
        return new ReportResponseDto(
                report.getId(),
                report.getTitle(),
                report.getDateFrom(),
                report.getDateTo(),
                report.getPdf(),
                report.getCreateAt(),
                report.getStudent().getName()
        );
    }
}
