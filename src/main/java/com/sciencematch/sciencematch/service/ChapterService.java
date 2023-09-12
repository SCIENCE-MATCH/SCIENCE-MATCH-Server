package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterPatchDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterPostDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterResponseDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterRequestDto;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.Question.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final QuestionRepository questionRepository;

    public List<ChapterResponseDto> getChapter(ChapterRequestDto chapterRequestDto) {
        return chapterRepository.getChapterList(chapterRequestDto.getSchool(),
                chapterRequestDto.getSemester(), chapterRequestDto.getSubject())
            .stream().map(ChapterResponseDto::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void patchChapter(ChapterPatchDto chapterPatchDto) {
        Chapter chapter = chapterRepository.getChapterById(chapterPatchDto.getId());
        chapter.changeDescription(chapterPatchDto.getDescription());
        chapterRepository.save(chapter);
    }

    @Transactional
    public Long postChapter(ChapterPostDto chapterPostDto) {
        Long parentId = chapterPostDto.getParentId();
        Chapter chapter;
        if (parentId != null) {
            chapter = Chapter.builder()
                .school(chapterPostDto.getSchool())
                .semester(chapterPostDto.getSemester())
                .subject(chapterPostDto.getSubject())
                .parent(chapterRepository.getChapterById(parentId))
                .description(chapterPostDto.getDescription())
                .build();
        } else {
            chapter = Chapter.builder()
                .school(chapterPostDto.getSchool())
                .semester(chapterPostDto.getSemester())
                .subject(chapterPostDto.getSubject())
                .parent(null)
                .description(chapterPostDto.getDescription())
                .build();
        }
        chapterRepository.save(chapter);
        return chapter.getId();
    }

    @Transactional
    public void deleteChapter(Long chapterId) {
        questionRepository.deleteAllInBatch(questionRepository.findAllByChapterId(chapterId));
        chapterRepository.deleteById(chapterId);
    }
}
