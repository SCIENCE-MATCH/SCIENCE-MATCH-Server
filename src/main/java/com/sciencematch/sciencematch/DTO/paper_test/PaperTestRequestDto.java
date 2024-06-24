package com.sciencematch.sciencematch.DTO.paper_test;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperTestRequestDto {

    @Schema(example = "HIGH")
    @NonNull
    private School school;
    @Schema(example = "SECOND1")
    @NonNull
    private Semester semester;
    @Schema(example = "9")
    @NonNull
    private Long chapterId;

    @Schema(example = "test 질문")
    private String question;
    @Schema(example = "test 답")
    private String solution;


}
