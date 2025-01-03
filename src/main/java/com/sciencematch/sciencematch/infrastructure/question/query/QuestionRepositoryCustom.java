package com.sciencematch.sciencematch.infrastructure.question.query;

import com.sciencematch.sciencematch.dto.csat.request.CsatRequestByNumDto;
import com.sciencematch.sciencematch.dto.csat.request.CsatRequestByChapterDto;
import com.sciencematch.sciencematch.dto.csat.response.CsatQuestionResponseDto;
import com.sciencematch.sciencematch.dto.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionResponseDto;
import java.util.List;

public interface QuestionRepositoryCustom {

    List<QuestionResponseDto> search(NormalQuestionPaperRequestDto normalQuestionPaperRequestDto);

    List<CsatQuestionResponseDto> searchCsat(List<CsatRequestByNumDto> csatRequestByNumDto);

    List<CsatQuestionResponseDto> searchCsatByChapter(
        CsatRequestByChapterDto csatRequestByChapterDto);

}
