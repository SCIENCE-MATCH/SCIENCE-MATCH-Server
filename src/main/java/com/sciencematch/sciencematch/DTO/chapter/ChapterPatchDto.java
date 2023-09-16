package com.sciencematch.sciencematch.DTO.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChapterPatchDto {

    @Schema(example = "9")
    private Long Id;
    @Schema(example = "개념 수정")
    private String description;
}
