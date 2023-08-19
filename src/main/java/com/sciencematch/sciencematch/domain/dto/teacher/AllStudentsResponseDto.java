package com.sciencematch.sciencematch.domain.dto.teacher;

import com.sciencematch.sciencematch.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllStudentsResponseDto {

    private Long id;
    private String grade;
    private String name;

    public static AllStudentsResponseDto of(Student student) {
        return new AllStudentsResponseDto(student.getId(), student.getGrade(), student.getName());
    }
}
