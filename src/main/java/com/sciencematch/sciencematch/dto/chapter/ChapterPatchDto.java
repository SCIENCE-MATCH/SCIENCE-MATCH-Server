package com.sciencematch.sciencematch.dto.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChapterPatchDto {

    @Schema(example = "9")
    @NotNull
    private Long Id;
    @Schema(example = "개념 수정")
    @NotEmpty
    private String description;
}
