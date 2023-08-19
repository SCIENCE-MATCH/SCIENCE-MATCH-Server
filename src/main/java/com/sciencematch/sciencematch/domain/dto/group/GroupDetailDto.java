package com.sciencematch.sciencematch.domain.dto.group;

import com.sciencematch.sciencematch.domain.Group;
import com.sciencematch.sciencematch.domain.dto.teacher.AllStudentsResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDetailDto {

    private Long groupId;
    private String groupName;
    private List<AllStudentsResponseDto> allStudents;

    @Builder
    public static GroupDetailDto of(Group group, List<AllStudentsResponseDto> allStudents) {
        return GroupDetailDto.builder()
            .groupId(group.getId())
            .groupName(group.getName())
            .allStudents(allStudents)
            .build();
    }

}
