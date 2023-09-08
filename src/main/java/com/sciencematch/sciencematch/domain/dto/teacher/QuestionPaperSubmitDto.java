package com.sciencematch.sciencematch.domain.dto.teacher;

import java.util.List;
import lombok.Data;

@Data
public class QuestionPaperSubmitDto {

    private List<Long> studentsId;
    private Long questionPaperId;

}
