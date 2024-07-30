package com.sciencematch.sciencematch.DTO.paper_test;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile image;
    @Schema(example = "test 질문")
    private String question;
    @Schema(example = "test 답")
    private String solution;
    private Subject subject;
    @Schema(example = "정인호")
    private String makerName;


}
