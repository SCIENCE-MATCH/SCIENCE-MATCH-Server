package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.QuestionPaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "학습지", description = "학습지 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
@RequestMapping("/teacher")
public class QuestionPaperController {

    private final QuestionPaperService questionPaperService;

    @PostMapping("/question-paper")
    @Operation(summary = "문제지 조회")
    public ApiResponseDto<List<QuestionPaperResponseDto>> getAllQuestionPaper(
        QuestionPaperSelectDto questionPaperSelectDto) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_PAPER_SUCCESS,
            questionPaperService.getAllQuestionPaper(questionPaperSelectDto));
    }

    @PostMapping("/questions/normal")
    @Operation(summary = "단원 유형별 학습지 생성시 범위 선택")
    public ApiResponseDto<List<QuestionResponseDto>> getNormalQuestions(
        NormalQuestionPaperRequestDto normalQuestionPaperRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_FOR_NORMAL_SUCCESS,
            questionPaperService.getNormalQuestions(normalQuestionPaperRequestDto));
    }
}