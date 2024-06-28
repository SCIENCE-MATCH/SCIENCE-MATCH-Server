package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.DTO.question.QuestionPostDto;
import com.sciencematch.sciencematch.DTO.question.QuestionRequestDto;
import com.sciencematch.sciencematch.DTO.question.AdminQuestionResponseDto;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionRepository;
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
    public void postQuestion(QuestionPostDto questionPostDto) {
        String image = s3Service.uploadFile(questionPostDto.getImage(), "question");
        String solutionImg = s3Service.uploadFile(questionPostDto.getSolutionImg(), "solution");
        Question question = Question.builder()
            .image(image)
            .level(questionPostDto.getLevel())
            .category(questionPostDto.getCategory())
            .solution(questionPostDto.getSolution())
            .solutionImg(solutionImg)
            .bookName(questionPostDto.getBookName())
            .page(questionPostDto.getPage())
            .pageOrder(questionPostDto.getPageOrder())
            .questionTag(questionPostDto.getQuestionTag())
            .chapterId(questionPostDto.getChapterId())
            .bookId(questionPostDto.getBookId())
            .score(questionPostDto.getScore())
            .build();
        questionRepository.save(question);
    }

    public List<AdminQuestionResponseDto> getQuestions(QuestionRequestDto questionRequestDto) {
        List<Long> chapterIds = new ArrayList<>();

        Chapter chapter = chapterRepository.getChapterById(questionRequestDto.getChapterId());
        chapterIds.add(questionRequestDto.getChapterId());

        findChapterList(chapter, chapterIds);
        return questionRepository.findAllByChapterIds(chapterIds, questionRequestDto.getLevel(),
                questionRequestDto.getCategory()).stream().map(AdminQuestionResponseDto::of)
            .collect(Collectors.toList());
    }

    public void deleteQuestion(Long questionId) throws IOException {
        Question question = questionRepository.getQuestionById(questionId);
        s3Service.deleteFile(question.getImage());
        questionRepository.delete(question);
    }

    private void findChapterList(Chapter chapter, List<Long> chapterIds) {
        List<Chapter> children = chapter.getChildren();
        if (children.isEmpty()) { //자식이 없는 경우엔 자신을 리스트에 추가
            chapterIds.add(chapter.getId());
            return;
        }
        for (Chapter targetChapter : children) {
            findChapterList(targetChapter, chapterIds);
        }
        chapterIds.add(chapter.getId()); //빠져 나오며 자기 자신을 리스트에 추가
    }
}
