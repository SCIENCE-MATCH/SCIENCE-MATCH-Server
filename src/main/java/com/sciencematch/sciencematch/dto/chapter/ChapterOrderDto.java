package com.sciencematch.sciencematch.dto.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class ChapterOrderDto {

    @Schema(description = "대상 단원의 부모 id, 없으면 null")
    private Long parentId;
    @Schema(description = "움직이는 대상 단원")
    private Long targetId;
    @Schema(description = "정렬된 단원 id")
    private List<Long> orderedChapterIds;

}
