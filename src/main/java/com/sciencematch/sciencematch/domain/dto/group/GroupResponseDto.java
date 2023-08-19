package com.sciencematch.sciencematch.domain.dto.group;

import com.sciencematch.sciencematch.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDto {

    private Long groupId;
    private String name;
    private String teacherName;

    public static GroupResponseDto of(Group group) {
        return new GroupResponseDto(group.getId(), group.getName(), group.getTeacher().getName());
    }
}
