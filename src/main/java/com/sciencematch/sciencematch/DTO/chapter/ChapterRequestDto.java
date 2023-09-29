package com.sciencematch.sciencematch.DTO.chapter;

import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChapterRequestDto {

    @Schema(example = "HIGH")
    @NotNull
    private School school;
    @Schema(example = "SECOND1")
    @NotNull
    private Semester semester;
    @Schema(example = "BIOLOGY")
    @NotNull
    private Subject subject;
}
