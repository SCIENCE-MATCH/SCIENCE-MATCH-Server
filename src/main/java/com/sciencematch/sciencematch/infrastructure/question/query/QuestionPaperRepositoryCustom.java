package com.sciencematch.sciencematch.infrastructure.question.query;

import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperSelectDto;
import java.util.List;

public interface QuestionPaperRepositoryCustom {

    List<QuestionPaperResponseDto> search(QuestionPaperSelectDto questionPaperSelectDto);

}
