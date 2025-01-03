package com.sciencematch.sciencematch.dto.question;

import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
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
