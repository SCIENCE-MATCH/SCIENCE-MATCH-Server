package com.sciencematch.sciencematch.DTO.question_paper;

import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.domain.TeacherLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherLevelResponseDto {

    private Long id;
    private Level level;
    private Integer low;
    private Integer mediumLow;
    private Integer medium;
    private Integer mediumHard;
    private Integer hard;

    public static TeacherLevelResponseDto of(TeacherLevel teacherLevel) {
        return new TeacherLevelResponseDto(
            teacherLevel.getId(),
            teacherLevel.getLevel(),
            (int) (teacherLevel.getLow() * 100),
            (int) (teacherLevel.getMediumLow() * 100),
            (int) (teacherLevel.getMedium() * 100),
            (int) (teacherLevel.getMediumHard() * 100),
            (int) (teacherLevel.getHard() * 100)
        );
    }
}
