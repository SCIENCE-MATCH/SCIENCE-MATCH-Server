package com.sciencematch.sciencematch.DTO.paper_test;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class PaperTestSubmitDto {

    @Schema(example = "52")
    private Long questionPaperId;
    @Schema(example = "[3, 4]", type = "array")
    private List<Long> studentIds;
}