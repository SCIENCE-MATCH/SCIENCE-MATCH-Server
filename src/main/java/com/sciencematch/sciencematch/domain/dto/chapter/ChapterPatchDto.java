package com.sciencematch.sciencematch.domain.dto.chapter;

import com.sciencematch.sciencematch.domain.enumerate.Semester;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class ChapterPatchDto {

    @Schema(example = "HIGH")
    private School school;
    @Schema(example = "SECOND")
    private Semester semester;
    @Schema(example = "BIOLOGY")
    private Subject subject;

    List<ChapterPatchDetailDto> chapterPatchDetailDtos;

}
