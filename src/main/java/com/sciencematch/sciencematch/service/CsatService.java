package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.chapter.ChapterResponseDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatIdsRequestDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatRequestByNumDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatRequestByChapterDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatForNumberResponseDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatQuestionResponseDto;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.CsatRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsatService {

    private final CsatRepository csatRepository;
    private final ChapterRepository chapterRepository;
    private final QuestionRepository questionRepository;

    public List<CsatForNumberResponseDto> getCsatIds(CsatIdsRequestDto csatIdsRequestDto) {
        return csatRepository.findAllBySubjectAndYearInAndMonthInAndPublisherIn(
                csatIdsRequestDto.getSubject(), csatIdsRequestDto.getYear(),
                csatIdsRequestDto.getMonth(), csatIdsRequestDto.getPublisher()).stream()
            .map(CsatForNumberResponseDto::of).collect(Collectors.toList());
    }

    public List<CsatQuestionResponseDto> getCsatQuestionByNum(
        List<CsatRequestByNumDto> csatRequestByNumDto) {
        return questionRepository.searchCsat(csatRequestByNumDto);
    }

    public List<ChapterResponseDto> getChapter(Subject subject) {
        return chapterRepository.findAllBySubject(subject)
            .stream().map(ChapterResponseDto::of)
            .collect(Collectors.toList());
    }

    public List<CsatQuestionResponseDto> getCsatQuestionByChapter(
        CsatRequestByChapterDto csatRequestByChapterDto) {
        return questionRepository.searchCsatByChapter(csatRequestByChapterDto);
    }
}
