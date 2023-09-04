package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.Question;
import com.sciencematch.sciencematch.domain.dto.question.QuestionPostDto;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.QuestionRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final S3Service s3Service;

    @Transactional
    public void postQuestion(QuestionPostDto questionPostDto) throws IOException {
        String image = s3Service.uploadImage(questionPostDto.getImage(), "question");
        Question question = Question.builder()
            .image(image)
            .level(questionPostDto.getLevel())
            .category(questionPostDto.getCategory())
            .answer(questionPostDto.getAnswer())
            .solution(questionPostDto.getSolution())
            .bookName(questionPostDto.getBookName())
            .page(questionPostDto.getPage())
            .build();
        questionRepository.save(question);
    }
}
