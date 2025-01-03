package com.sciencematch.sciencematch.dto.auth.response;

import com.sciencematch.sciencematch.domain.Student;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class StudentResponseDto {

    private String phoneNum;
    private String name;

    public static StudentResponseDto of(Student student) {
        return new StudentResponseDto(student.getPhoneNum(), student.getName());
    }

}
