package com.sciencematch.sciencematch.DTO.student;

import java.util.List;
import lombok.Data;

@Data
public class AssignPaperTestSolveDto {

    private Long assignPaperTestId;
    private List<String> answer;

}
