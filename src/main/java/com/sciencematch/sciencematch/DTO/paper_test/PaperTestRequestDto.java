package com.sciencematch.sciencematch.DTO.paper_test;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
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

    @Schema(example = "예시 제목")
    @NotBlank
    private String title;

    private List<PaperTestQuestionDto> paperTestQuestionDtos;

}
