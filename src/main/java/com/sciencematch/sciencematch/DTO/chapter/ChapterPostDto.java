package com.sciencematch.sciencematch.DTO.chapter;

import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChapterPostDto {

    @Schema(example = "HIGH")
    @NotBlank
    private School school;
    @Schema(example = "SECOND")
    @NotBlank
    private Semester semester;
    @Schema(example = "BIOLOGY")
    @NotEmpty
    private Subject subject;

    private Long parentId;
    @NotNull
    private String description;

}
