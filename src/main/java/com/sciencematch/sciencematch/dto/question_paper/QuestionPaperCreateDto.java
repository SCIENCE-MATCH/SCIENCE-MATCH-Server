package com.sciencematch.sciencematch.dto.question_paper;

import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.enums.QuestionTag;
import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Semester;
import com.sciencematch.sciencematch.enums.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    @Schema(example = "FIRST1")
    private Semester semester;

    @Schema(example = "MULTIPLE")
    private Category category; //객관, 주관, 서술
    @Schema(example = "NORMAL")
    private QuestionTag questionTag; //단원별, 시중교재, 모의고사
    @Schema(example = "BIOLOGY")
    private Subject subject; //과목
    @Schema(example = "MEDIUM")
    private Level level;

    @Schema(example = "#000000")
    private String themeColor;
    @Schema(example = "0")
    private Integer template;
    private MultipartFile pdf;

    private Long minChapterId;
    private Long maxChapterId;
}
