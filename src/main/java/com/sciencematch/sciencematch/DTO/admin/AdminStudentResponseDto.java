package com.sciencematch.sciencematch.DTO.admin;

import com.sciencematch.sciencematch.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminStudentResponseDto {

    private Long studentId;
    private String name;
    private String grade;
    private String teacherName;
    private String phoneNum;

    public static AdminStudentResponseDto of(Student student) {
        return new AdminStudentResponseDto(student.getId(), student.getName(), student.getGrade(),
            student.getTeacher().getName(), student.getPhoneNum());
    }

}
