package com.sciencematch.sciencematch.domain.dto.question_paper;

import com.sciencematch.sciencematch.domain.enumerate.QuestionTag;
import com.sciencematch.sciencematch.domain.enumerate.School;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class QuestionPaperSelectDto {

    @Schema(example = "HIGH")
    private School school;
    @Schema(example = "NORMAL")
    private QuestionTag questionTag;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(example = "2023-09-08T05:55:58")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(example = "2023-12-08T05:55:58")
    private LocalDateTime end;
}
