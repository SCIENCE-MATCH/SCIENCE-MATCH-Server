package com.sciencematch.sciencematch.dto.question_paper;

// import com.sciencematch.sciencematch.enums.QuestionTag;
// import com.sciencematch.sciencematch.enums.School;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class QuestionPaperSelectDto {

    // @Schema(example = "HIGH")
    // private School school;
    // @Schema(example = "NORMAL")
    // private QuestionTag questionTag;

    @Schema(example = "2023-09-08T05:55:58", type = "string")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start;

    @Schema(example = "2024-12-08T05:55:58", type = "string")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end;
}
