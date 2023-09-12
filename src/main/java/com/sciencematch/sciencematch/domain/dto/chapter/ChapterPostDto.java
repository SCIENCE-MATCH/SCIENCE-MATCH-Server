package com.sciencematch.sciencematch.domain.dto.chapter;

import com.sciencematch.sciencematch.domain.enumerate.Semester;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChapterPostDto {

    @Schema(example = "HIGH")
    private School school;
    @Schema(example = "SECOND")
    private Semester semester;
    @Schema(example = "BIOLOGY")
    private Subject subject;

    private Long parentId;
    private String description;

}
