package com.sciencematch.sciencematch.domain.dto.admin;

import com.sciencematch.sciencematch.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminTeamResponseDto {

    private Long id;
    private String academy;
    private String teacherName;
    private String teamName;
    private Integer studentsCount;

    public static AdminTeamResponseDto of(Team team) {
        return new AdminTeamResponseDto(team.getId(), team.getTeacher().getAcademy(),
            team.getTeacher().getName(), team.getName(), team.getStudentsNum());
    }

}
