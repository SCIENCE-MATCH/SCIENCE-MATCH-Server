package com.sciencematch.sciencematch.domain.dto.question;

import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QuestionRequestDto {

    @Schema(example = "9")
    private Long chapterId;
    @Schema(example = "MEDIUM")
    private Level level;
    @Schema(example = "MULTIPLE")
    private Category category;

}
