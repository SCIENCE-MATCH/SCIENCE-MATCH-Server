package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.dto.ChapterResponseDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterRequestDto;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public List<ChapterResponseDto> getChapter(ChapterRequestDto chapterRequestDto) {
        return chapterRepository.getChapterList(chapterRequestDto.getSchool(),
                chapterRequestDto.getGrade(), chapterRequestDto.getSubject())
            .stream().map(ChapterResponseDto::of)
            .collect(Collectors.toList());
    }

}
