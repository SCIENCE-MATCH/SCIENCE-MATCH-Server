package com.sciencematch.sciencematch.domain.dto.team;

import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.domain.dto.teacher.SimpleStudentsResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TeamDetailDto {

    private Long groupId;
    private String groupName;
    private List<SimpleStudentsResponseDto> allStudents;

    @Builder
    public static TeamDetailDto of(Team team) {
        return TeamDetailDto.builder()
            .groupId(team.getId())
            .groupName(team.getName())
            .allStudents(team.getTeamStudents().stream().map(groupStudent -> SimpleStudentsResponseDto.of(groupStudent.getStudent())).collect(
                Collectors.toList()))
            .build();
    }

}
