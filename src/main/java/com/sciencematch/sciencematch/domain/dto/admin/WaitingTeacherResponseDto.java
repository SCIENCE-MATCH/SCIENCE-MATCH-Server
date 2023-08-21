package com.sciencematch.sciencematch.domain.dto.admin;

import com.sciencematch.sciencematch.domain.Teacher;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaitingTeacherResponseDto {

    private Long teacherId;
    private String name;
    private String email;
    private String phoneNum;
    private LocalDateTime createdAt;

    public static WaitingTeacherResponseDto of(Teacher teacher) {
        return new WaitingTeacherResponseDto(teacher.getId(), teacher.getName(), teacher.getEmail(),
            teacher.getPhoneNum(), teacher.getCreateAt());
    }

}
