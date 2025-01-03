package com.sciencematch.sciencematch.dto.auth.response;

import com.sciencematch.sciencematch.domain.Teacher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class TeacherResponseDto {
    private String email;
    private String name;

    public static TeacherResponseDto of(Teacher teacher) {
        return new TeacherResponseDto(teacher.getEmail(), teacher.getName());
    }
}
