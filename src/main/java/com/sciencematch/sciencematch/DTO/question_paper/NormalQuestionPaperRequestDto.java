package com.sciencematch.sciencematch.DTO.question_paper;

import com.sciencematch.sciencematch.Enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

@Data
public class NormalQuestionPaperRequestDto {

    //공통 조회 데이터
    @Schema(example = "[10, 11]", type = "array")
    private List<Long> chapterIds;

    @Schema(example = "10")
    private Integer questionNum;
    @Schema(example = "0.4")
    private Double low;
    @Schema(example = "0.4")
    private Double mediumLow;
    @Schema(example = "0.2")
    private Double medium;
    @Schema(example = "0")
    private Double mediumHigh;
    @Schema(example = "0")
    private Double high;
    @Schema(example = "[MULTIPLE]", type = "array")
    private List<Category> category;
    @Schema(example = "false")
    private Boolean mockExam; //모의고사 포함, 제외, 모의고사만

    public List<Integer> getSelectCount() {
        return Arrays.asList(
            (int) (questionNum * low),
            (int) (questionNum * mediumLow),
            (int) (questionNum * medium),
            (int) (questionNum * mediumHigh),
            (int) (questionNum * high)
        );
    }

}
