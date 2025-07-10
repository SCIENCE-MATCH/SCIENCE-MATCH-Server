package com.sciencematch.sciencematch.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Data
public class ReportCreateDto {

    @Schema(example = "새로운 보고서")
    private String title;
    @Schema(example = "2024-03-01T00:00:00")
    private LocalDateTime dateFrom;
    @Schema(example = "2024-03-31T23:59:59")
    private LocalDateTime dateTo;
    @Schema(example = "0")
    private Long studentId;

    private MultipartFile pdf;

}
