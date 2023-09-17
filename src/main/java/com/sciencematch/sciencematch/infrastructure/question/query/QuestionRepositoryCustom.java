package com.sciencematch.sciencematch.infrastructure.question.query;

import com.sciencematch.sciencematch.DTO.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionResponseDto;
import java.util.List;

public interface QuestionRepositoryCustom {

    List<QuestionResponseDto> search(NormalQuestionPaperRequestDto normalQuestionPaperRequestDto);

}
