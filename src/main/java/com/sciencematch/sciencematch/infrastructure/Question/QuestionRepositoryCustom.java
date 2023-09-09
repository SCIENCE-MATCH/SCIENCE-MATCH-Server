package com.sciencematch.sciencematch.infrastructure.Question;

import com.sciencematch.sciencematch.domain.dto.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionResponseDto;
import java.util.List;

public interface QuestionRepositoryCustom {

    List<QuestionResponseDto> search(NormalQuestionPaperRequestDto normalQuestionPaperRequestDto);

}
