package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperSelectDto;
import java.util.List;

public interface QuestionPaperRepositoryCustom {

    List<QuestionPaperResponseDto> search(QuestionPaperSelectDto questionPaperSelectDto);

}
