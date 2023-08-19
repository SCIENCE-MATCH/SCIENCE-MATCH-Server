package com.sciencematch.sciencematch.domain.dto.groups.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class GroupRequestDto {

    @Schema(example = "예시 반")
    private String groupName;
    @Schema(example = "[1, 2]")
    private List<Long> studentIds;
}
