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
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;
}
