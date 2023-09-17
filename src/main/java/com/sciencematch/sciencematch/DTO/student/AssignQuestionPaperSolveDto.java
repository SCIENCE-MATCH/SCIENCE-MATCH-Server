package com.sciencematch.sciencematch.DTO.student;

import java.util.List;
import lombok.Data;

@Data
public class AssignQuestionPaperSolveDto {

    private Long assignQuestionPaperId;
    private List<String> answer;

}
