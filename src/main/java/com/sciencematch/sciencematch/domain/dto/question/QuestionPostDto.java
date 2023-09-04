package com.sciencematch.sciencematch.domain.dto.question;

import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
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
    private String answer;

    @Schema(example = "답이 1번이기 때문에 1번입니다.")
    private String solution;

    @Schema(example = "비상비상")
    private String bookName;

    @Schema(example = "17")
    private Integer page;

}
