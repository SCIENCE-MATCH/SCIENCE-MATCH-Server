package com.sciencematch.sciencematch.DTO.teacher;

import com.sciencematch.sciencematch.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleStudentsResponseDto {

    private Long id;
    private String grade;
    private String name;

    public static SimpleStudentsResponseDto of(Student student) {
        return new SimpleStudentsResponseDto(student.getId(), student.getGrade(), student.getName());
    }
}
