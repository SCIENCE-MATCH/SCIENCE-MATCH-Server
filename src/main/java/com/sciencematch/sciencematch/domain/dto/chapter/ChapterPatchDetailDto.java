package com.sciencematch.sciencematch.domain.dto.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class ChapterPatchDetailDto {

    @Schema(example = "테스트")
    private String description;
    @Schema(example = "[]")
    private List<ChapterPatchDetailDto> subunit;
}
