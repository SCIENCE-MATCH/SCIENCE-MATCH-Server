package com.sciencematch.sciencematch.DTO.paper_test;

import com.sciencematch.sciencematch.Enums.School;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PaperTestSelectDto {
    @Schema(example = "HIGH")
    private School school;

    @Schema(example = "2023-09-08T05:55:58", type = "string")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start;

    @Schema(example = "2024-12-08T05:55:58", type = "string")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;
}
