package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.DTO.concept.ConceptResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperCreateDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperDownloadDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperDownloadRequestDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.request.MultipleQuestionPaperSubmitDto;
import com.sciencematch.sciencematch.DTO.teacher.request.QuestionPaperSubmitDto;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.QuestionPaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Operation(summary = "학습지 조회")
    public ApiResponseDto<List<QuestionPaperResponseDto>> getAllQuestionPaper(
        @RequestBody QuestionPaperSelectDto questionPaperSelectDto) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_PAPER_SUCCESS,
            questionPaperService.getAllQuestionPaper(questionPaperSelectDto));
    }

    @PostMapping("/question-paper/concept")
    @Operation(summary = "개념 조회")
    public ApiResponseDto<List<ConceptResponseDto>> getQuestionPaperConcepts(
        @RequestBody List<Long> chapterIds) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_CONCEPT_SUCCESS,
            questionPaperService.getQuestionPaperConcepts(chapterIds));
    }

    @PostMapping("/questions/normal")
    @Operation(summary = "단원 유형별 학습지 범위 선택")
    public ApiResponseDto<List<QuestionResponseDto>> getNormalQuestions(
        NormalQuestionPaperRequestDto normalQuestionPaperRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_FOR_NORMAL_SUCCESS,
            questionPaperService.getNormalQuestions(normalQuestionPaperRequestDto));
    }

    @PostMapping(value = "/question-paper/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "학습지 생성")
    public ApiResponseDto<?> createQuestionPaper(
        @ModelAttribute QuestionPaperCreateDto questionPaperCreateDto) {
        questionPaperService.createQuestionPaper(questionPaperCreateDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_QUESTION_PAPER_SUCCESS);
    }

    @PostMapping("/question-paper/submit")
    @Operation(summary = "단일 문제지 출제")
    public ApiResponseDto<?> submitQuestionPaper(
        @RequestBody QuestionPaperSubmitDto questionPaperSubmitDto) {
        questionPaperService.submitQuestionPaper(questionPaperSubmitDto);
        return ApiResponseDto.success(SuccessStatus.SUBMIT_QUESTION_PAPER_SUCCESS);
    }

    @PostMapping("/question-paper/multiple-submit")
    @Operation(summary = "복수 문제지 출제")
    public ApiResponseDto<?> submitMultipleQuestionPaper(
        MultipleQuestionPaperSubmitDto multipleQuestionPaperSubmitDto) {
        questionPaperService.submitMultipleQuestionPaper(multipleQuestionPaperSubmitDto);
        return ApiResponseDto.success(SuccessStatus.SUBMIT_QUESTION_PAPER_SUCCESS);
    }

    @PostMapping("/question-paper/delete")
    @Operation(summary = "학습지 삭제")
    public ApiResponseDto<?> deleteQuestionPaper(@RequestBody List<Long> questionPaperIds) {
        questionPaperService.deleteQuestionPaper(questionPaperIds);
        return ApiResponseDto.success(SuccessStatus.DELETE_QUESTION_PAPER_SUCCESS);
    }

    @PostMapping("/question-paper/wrong")
    @Operation(summary = "오답 문제 추출")
    public ApiResponseDto<List<QuestionResponseDto>> getWrongQuestion(
        @RequestBody List<Long> assignQuestionIds) {
        return ApiResponseDto.success(SuccessStatus.GET_WRONG_SUCCESS,
            questionPaperService.getWrongQuestion(assignQuestionIds));
    }

    @GetMapping("/question-paper/download")
    @Operation(summary = "학습지 다운로드")
    public ApiResponseDto<QuestionPaperDownloadDto> downloadQuestionPaper(
        QuestionPaperDownloadRequestDto questionPaperDownloadRequestDto) {
        return ApiResponseDto.success(SuccessStatus.QUESTION_PAPER_DOWNLOAD_SUCCESS,
            questionPaperService.downloadQuestionPaper(questionPaperDownloadRequestDto));
    }
}
