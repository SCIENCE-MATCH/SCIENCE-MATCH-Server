package com.sciencematch.sciencematch.DTO.paper_test;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class PaperTestRequestDto {

    @Schema(example = "HIGH")
    private School school;
    @Schema(example = "SECOND1")
    private Semester semester;
    @Schema(example = "9")
    private Long chapterId;

    @Schema(example = "예시 제목")
    private String title;

    private List<PaperTestQuestionDto> paperTestQuestionDtos;

}
