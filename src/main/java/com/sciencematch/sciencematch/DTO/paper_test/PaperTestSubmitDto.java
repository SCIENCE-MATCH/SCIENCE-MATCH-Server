package com.sciencematch.sciencematch.DTO.paper_test;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperTestSubmitDto {

    @Schema(example = "52")
    @NonNull
    private Long questionPaperId;
    @Schema(example = "[3, 4]", type = "array")
    private List<Long> studentIds;
}
