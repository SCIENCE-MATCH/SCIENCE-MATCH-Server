package com.sciencematch.sciencematch.dto.auth.response;

import com.sciencematch.sciencematch.domain.Teacher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageDto {

    private String id;
    private String name;
    private String phoneNum;
    private String academy;
    private String logo;

    public static MyPageDto of(Teacher teacher) {
        return new MyPageDto(teacher.getEmail(), teacher.getName(), teacher.getPhoneNum(),
            teacher.getAcademy(), teacher.getLogo());
    }
}
