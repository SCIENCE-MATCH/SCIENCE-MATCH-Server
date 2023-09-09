package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.domain.dto.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionResponseDto;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import com.sciencematch.sciencematch.infrastructure.Question.QuestionPaperRepository;
import com.sciencematch.sciencematch.infrastructure.Question.QuestionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionPaperService {

    private final QuestionPaperRepository questionPaperRepository;
    private final QuestionRepository questionRepository;

    public List<QuestionPaperResponseDto> getAllQuestionPaper(
        QuestionPaperSelectDto questionPaperSelectDto) {
        return questionPaperRepository.search(questionPaperSelectDto);
    }

    //단원 유형별 자동 생성된 학습지 반환
    public List<QuestionResponseDto> getNormalQuestions(
        NormalQuestionPaperRequestDto normalQuestionPaperRequestDto) {
        //레벨 관계 없이 데이터 조회
        List<QuestionResponseDto> search = questionRepository.search(normalQuestionPaperRequestDto);
        Collections.shuffle(search);
        List<Integer> selectCount = getSelectCount(normalQuestionPaperRequestDto.getLevel(),
            normalQuestionPaperRequestDto.getQuestionNum());
        return makeNormalQuestionList(search, selectCount, normalQuestionPaperRequestDto.getQuestionNum());
    }

    private List<QuestionResponseDto> makeNormalQuestionList( //난이도별 문제 개수에 맞게 선택해 반환
        List<QuestionResponseDto> questionResponseDtos, List<Integer> selectCount, Integer count) {
        List<QuestionResponseDto> result = new ArrayList<>();
        for (QuestionResponseDto q : questionResponseDtos) {
            switch (q.getLevel()) {
                case LOW:
                    if (selectCount.get(0) > 0) {
                        result.add(q);
                        selectCount.set(0, selectCount.get(0) - 1);
                    }
                    break;
                case MEDIUM_LOW:
                    if (selectCount.get(1) > 0) {
                        result.add(q);
                        selectCount.set(1, selectCount.get(1) - 1);
                    }
                    break;
                case MEDIUM:
                    if (selectCount.get(2) > 0) {
                        result.add(q);
                        selectCount.set(2, selectCount.get(2) - 1);
                    }
                    break;
                case MEDIUM_HARD:
                    if (selectCount.get(3) > 0) {
                        result.add(q);
                        selectCount.set(3, selectCount.get(3) - 1);
                    }
                    break;
                case HARD:
                    if (selectCount.get(4) > 0) {
                        result.add(q);
                        selectCount.set(4, selectCount.get(4) - 1);
                    }
                    break;
            }
            if (result.size() == count) {
                break;
            }
        }
        return result;

    }

    private List<Integer> getSelectCount(Level level, Integer questionNum) { //난이도별 문제 개수 설정
        switch (level) {
            case LOW:
                return Arrays.asList(questionNum * 2 / 5, questionNum * 2 / 5, questionNum / 5,
                    0, 0);
            case MEDIUM_LOW:
                return Arrays.asList(questionNum / 5, questionNum * 2 / 5, questionNum * 3 / 10,
                    questionNum / 10, 0);
            case MEDIUM:
                return Arrays.asList(questionNum / 10, questionNum * 3 / 10, questionNum * 3 / 10,
                    questionNum / 5, questionNum / 10);
            case MEDIUM_HARD:
                return Arrays.asList(0, questionNum / 5, questionNum * 3 / 10,
                    questionNum * 3 / 10, questionNum / 5);
            case HARD:
                return Arrays.asList(0, 0, questionNum * 3 / 10,
                    questionNum * 3 / 10, questionNum * 2 / 5);
        }
        return null;
    }
}
