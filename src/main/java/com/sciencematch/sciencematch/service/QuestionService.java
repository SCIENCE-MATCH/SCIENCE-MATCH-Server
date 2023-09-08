package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Question;
import com.sciencematch.sciencematch.domain.dto.question.QuestionPostDto;
import com.sciencematch.sciencematch.domain.dto.question.QuestionRequestDto;
import com.sciencematch.sciencematch.domain.dto.question.QuestionResponseDto;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.Question.QuestionRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChapterRepository chapterRepository;

    private final S3Service s3Service;

    @Transactional
    public void postQuestion(QuestionPostDto questionPostDto) throws IOException {
        String image = s3Service.uploadImage(questionPostDto.getImage(), "question");
        Chapter chapter = chapterRepository.getChapterById(questionPostDto.getChapterId());
        Question question = Question.builder()
            .image(image)
            .level(questionPostDto.getLevel())
            .category(questionPostDto.getCategory())
            .answer(questionPostDto.getAnswer())
            .solution(questionPostDto.getSolution())
            .bookName(questionPostDto.getBookName())
            .page(questionPostDto.getPage())
            .chapter(chapter)
            .build();
        questionRepository.save(question);
    }

    public List<QuestionResponseDto> getQuestions(QuestionRequestDto questionRequestDto) {
        Chapter chapter = chapterRepository.getChapterById(questionRequestDto.getChapterId());
        List<Chapter> chapterList = new ArrayList<>();
        chapterList.add(chapter);
        findChapterList(chapter, chapterList);
        return questionRepository.findAllByChapters(chapterList, questionRequestDto.getLevel(),
                questionRequestDto.getCategory()).stream().map(QuestionResponseDto::of)
            .collect(Collectors.toList());
    }

    public void deleteQuestion(Long questionId) throws IOException {
        Question question = questionRepository.getQuestionById(questionId);
        s3Service.deleteFile(question.getImage());
        questionRepository.delete(question);
    }

    private void findChapterList(Chapter chapter, List<Chapter> chapterList) {
        List<Chapter> children = chapter.getChildren();
        if (children.size() == 0) { //자식이 없는 경우엔 자신을 리스트에 추가
            chapterList.add(chapter);
            return;
        }
        for (Chapter targetChapter : children) {
            findChapterList(targetChapter, chapterList);
        }
        chapterList.add(chapter); //빠져 나오며 자기 자신을 리스트에 추가
    }
}
