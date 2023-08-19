package com.sciencematch.sciencematch.domain.dto.groups;

import com.sciencematch.sciencematch.domain.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupResponseDto {

    private Long groupId;
    private String name;
    private String teacherName;

    public static GroupResponseDto of(Groups groups) {
        return new GroupResponseDto(groups.getId(), groups.getName(), groups.getTeacher().getName());
    }
}
