package com.sciencematch.sciencematch.domain.dto.team;

import com.sciencematch.sciencematch.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamResponseDto {

    private Long teamId;
    private String name;
    private String teacherName;

    public static TeamResponseDto of(Team team) {
        return new TeamResponseDto(team.getId(), team.getName(), team.getTeacher().getName());
    }
}
