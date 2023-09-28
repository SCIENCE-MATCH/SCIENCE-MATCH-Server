package com.sciencematch.sciencematch.DTO.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChapterPatchDto {

    @Schema(example = "9")
    @NotBlank
    private Long Id;
    @Schema(example = "개념 수정")
    @NotEmpty
    private String description;
}
