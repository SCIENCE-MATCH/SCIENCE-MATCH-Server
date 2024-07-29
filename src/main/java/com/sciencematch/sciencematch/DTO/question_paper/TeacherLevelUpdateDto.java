package com.sciencematch.sciencematch.DTO.question_paper;

import lombok.Data;

@Data
public class TeacherLevelUpdateDto {

    private Long id;
    private Integer low;
    private Integer mediumLow;
    private Integer medium;
    private Integer mediumHard;
    private Integer hard;
}
