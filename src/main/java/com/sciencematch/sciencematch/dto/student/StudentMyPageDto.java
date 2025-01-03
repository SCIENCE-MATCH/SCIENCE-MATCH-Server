package com.sciencematch.sciencematch.dto.student;

import com.sciencematch.sciencematch.domain.Student;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class StudentMyPageDto {
    private String name;
    private String phoneNum;
    private String parentNum;

    public static StudentMyPageDto of(Student student) {
        return new StudentMyPageDto(student.getName(), student.getPhoneNum(), student.getParentNum());
    }
}
