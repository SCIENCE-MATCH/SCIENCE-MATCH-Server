package com.sciencematch.sciencematch.domain.dto.question_paper;

import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.QuestionTag;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Semester;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class QuestionPaperCreateDto {

    @Schema(example = "새로 만든 문제지")
    private String title;
    @Schema(example = "오펜하이머")
    private String makerName;
    @Schema(type = "array", example = "[17, 18, 19, 20, 21, 22, 23, 24]")
    private List<Long> questionIds;
    @Schema(example = "8")
    private Integer questionNum;

    @Schema(example = "HIGH")
    private School school;
    @Schema(example = "FIRST")
    private Semester semester;

    @Schema(example = "MULTIPLE")
    private Category category; //객관, 주관, 서술
    @Schema(example = "NORMAL")
    private QuestionTag questionTag; //단원별, 시중교재, 모의고사
    @Schema(example = "BIOLOGY")
    private Subject subject; //과목

}
