package com.sciencematch.sciencematch.domain.dto.paper_test;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class MultiplePaperTestSubmitDto {

    @Schema(type = "array", example = "[3, 4]")
    private List<Long> studentIds;

    @Schema(type = "array", example = "[52]")
    private List<Long> paperTestIds;
}
