package com.sciencematch.sciencematch.dto.question_paper;

import lombok.Data;

@Data
public class QuestionPaperDownloadRequestDto {

    private Long questionPaperId;
    private Boolean question;
    private Boolean solution;
    private Boolean solutionImage;

}
