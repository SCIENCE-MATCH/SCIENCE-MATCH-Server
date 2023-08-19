package com.sciencematch.sciencematch.domain.dto.teacher;

import com.sciencematch.sciencematch.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MyStudentsResponseDto {

    private Long id;
    private String grade;
    private Boolean deleted;
    private String name;
    private String parentNum;
    private String phoneNum;

    public static MyStudentsResponseDto of(Student student) {
        return MyStudentsResponseDto.builder()
            .id(student.getId())
            .grade(student.getGrade())
            .deleted(student.getDeleted())
            .name(student.getName())
            .parentNum(student.getParentNum())
            .phoneNum(student.getPhoneNum())
            .build();
    }

}
