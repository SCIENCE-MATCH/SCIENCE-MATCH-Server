package com.sciencematch.sciencematch.domain.dto.groups;

import com.sciencematch.sciencematch.domain.Groups;
import com.sciencematch.sciencematch.domain.dto.teacher.AllStudentsResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GroupDetailDto {

    private Long groupId;
    private String groupName;
    private List<AllStudentsResponseDto> allStudents;

    @Builder
    public static GroupDetailDto of(Groups groups) {
        return GroupDetailDto.builder()
            .groupId(groups.getId())
            .groupName(groups.getName())
            .allStudents(groups.getGroupStudents().stream().map(groupStudent -> AllStudentsResponseDto.of(groupStudent.getStudent())).collect(
                Collectors.toList()))
            .build();
    }

}
