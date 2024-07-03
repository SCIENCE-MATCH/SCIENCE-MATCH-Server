package com.sciencematch.sciencematch.DTO.question_paper;

import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class NormalQuestionPaperRequestDto {

    //공통 조회 데이터
    @Schema(example = "[10, 11]", type = "array")
    private List<Long> chapterIds;

    @Schema(example = "10")
    private Integer questionNum;
    @Schema(example = "MEDIUM")
    private Level level;
    @Schema(example = "[MULTIPLE]", type = "array")
    private List<Category> category;
    @Schema(example = "false")
    private Boolean mockExam; //모의고사 포함, 제외, 모의고사만


}
