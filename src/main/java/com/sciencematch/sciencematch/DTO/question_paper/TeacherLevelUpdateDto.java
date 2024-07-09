package com.sciencematch.sciencematch.DTO.question_paper;

import com.sciencematch.sciencematch.Enums.Level;
import lombok.Data;

@Data
public class TeacherLevelUpdateDto {

    private Long id;
    private Level level;
    private Integer low;
    private Integer mediumLow;
    private Integer medium;
    private Integer mediumHard;
    private Integer hard;
}
