package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterPatchDetailDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterPatchDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterResponseDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterRequestDto;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public List<ChapterResponseDto> getChapter(ChapterRequestDto chapterRequestDto) {
        return chapterRepository.getChapterList(chapterRequestDto.getSchool(),
                chapterRequestDto.getSemester(), chapterRequestDto.getSubject())
            .stream().map(ChapterResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public String patchChapter(ChapterPatchDto chapterPatchDto) {
        chapterRepository.deleteAll();
        for (ChapterPatchDetailDto chapterPatchDetailDto:chapterPatchDto.getChapterPatchDetailDtos()) {
            createChapter(chapterPatchDto, chapterPatchDetailDto, null);
        }
        return "success";
    }

    @Transactional
    public void createChapter(ChapterPatchDto chapterPatchDto, ChapterPatchDetailDto chapterPatchDetailDto, Chapter id) {
        Chapter chapter = Chapter.builder()
            .school(chapterPatchDto.getSchool())
            .semester(chapterPatchDto.getSemester())
            .subject(chapterPatchDto.getSubject())
            .parent(id)
            .description(chapterPatchDetailDto.getDescription())
            .build();
        chapterRepository.save(chapter);
        List<ChapterPatchDetailDto> subunit = chapterPatchDetailDto.getSubunit();
        if (!subunit.isEmpty()) { //하위 챕터가 있는 경우
            for (ChapterPatchDetailDto patchDetailDto : subunit) {
                createChapter(chapterPatchDto, patchDetailDto, chapter);
            }
        }
    }

}
