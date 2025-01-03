package com.sciencematch.sciencematch.dto.teacher.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class SummaryRequestDto {

    @Schema(example = "2")
    private Long studentId;
    @Schema(example = "2023-09-08T05:55:58", type = "string")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @Schema(example = "2024-09-08T05:55:58", type = "string")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
}
