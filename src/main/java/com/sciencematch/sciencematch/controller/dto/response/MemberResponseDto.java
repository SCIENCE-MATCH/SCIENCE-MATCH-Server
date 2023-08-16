package com.sciencematch.sciencematch.controller.dto.response;

import com.sciencematch.sciencematch.domain.Teacher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String name;

    public static MemberResponseDto of(Teacher teacher) {
        return new MemberResponseDto(teacher.getEmail(), teacher.getName());
    }
}
