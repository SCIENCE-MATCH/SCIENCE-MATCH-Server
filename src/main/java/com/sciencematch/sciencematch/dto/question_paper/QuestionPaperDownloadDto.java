package com.sciencematch.sciencematch.dto.question_paper;

import com.sciencematch.sciencematch.domain.question.Question;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPaperDownloadDto {

    List<String> questions;
    List<String> solutions;
    List<String> solutionImages;

    public static QuestionPaperDownloadDto of(List<Question> questions,
        QuestionPaperDownloadRequestDto questionPaperDownloadRequestDto) {
        return new QuestionPaperDownloadDto(
            questionPaperDownloadRequestDto.getQuestion() ? questions.stream().map(Question::getImage).collect(Collectors.toList()) : null,
            questionPaperDownloadRequestDto.getSolution() ? questions.stream().map(Question::getSolution).collect(Collectors.toList()) : null,
            questionPaperDownloadRequestDto.getSolutionImage() ? questions.stream().map(Question::getSolutionImg).collect(Collectors.toList()) : null
        );
    }

}
