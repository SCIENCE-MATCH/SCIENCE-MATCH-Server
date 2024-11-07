package com.sciencematch.sciencematch.DTO.question;

import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class QuestionPostDto {

    private MultipartFile image;

    @Schema(example = "MEDIUM")
    private Level level;

    @Schema(example = "MULTIPLE")
    private Category category;

    @Schema(example = "1")
    private String solution;

    private MultipartFile solutionImg;

    @Schema(example = "비상비상")
    private String bookName;

    @Schema(example = "17")
    private Integer page;

    @Schema(example = "2.1")
    private Double pageOrder;

    @Schema(example = "NORMAL")
    private QuestionTag questionTag;

    @Schema(example = "7")
    private Long chapterId;
    @Schema(example = "33")
    private Long bookId;
    @Schema(example = "7")
    private Long csatId;
    @Schema(example = "3")
    private Integer score;

}
