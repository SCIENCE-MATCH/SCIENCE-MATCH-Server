package com.sciencematch.sciencematch.domain.dto.team.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class TeamRequestDto {

    @Schema(example = "예시 반")
    private String teamName;
    @Schema(example = "[1, 2]")
    private List<Long> studentIds;
}
