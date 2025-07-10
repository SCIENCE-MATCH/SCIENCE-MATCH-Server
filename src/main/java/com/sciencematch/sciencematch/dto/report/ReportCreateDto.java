package com.sciencematch.sciencematch.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Data
public class ReportCreateDto {

    @Schema(example = "새로운 보고서")
    private String title;
    @Schema(example = "2025.06.11~2025.07.11")
    private String period;
    @Schema(example = "0")
    private Long studentId;

    private MultipartFile pdf;

}
