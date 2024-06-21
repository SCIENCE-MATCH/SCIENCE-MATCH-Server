package com.sciencematch.sciencematch.DTO.team.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamRequestDto {

    @Schema(example = "예시 반")
    @NotBlank
    private String teamName;
    @Schema(example = "예시 선생님 이름")
    @NotBlank
    private String teacherName;
    @Schema(example = "[1, 2]")
    private List<Long> studentIds;
}
