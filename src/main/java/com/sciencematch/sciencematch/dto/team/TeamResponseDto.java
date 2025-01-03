package com.sciencematch.sciencematch.dto.team;

import com.sciencematch.sciencematch.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamResponseDto {

    private Long teamId;
    private String name;
    private String teacherName;
    private Integer studentNum;

    public static TeamResponseDto of(Team team) {
        return new TeamResponseDto(team.getId(), team.getName(), team.getTeacherName(),
            team.getTeamStudents().size());
    }
}
