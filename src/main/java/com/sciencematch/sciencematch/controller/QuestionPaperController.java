package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.dto.concept.ConceptResponseDto;
import com.sciencematch.sciencematch.dto.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperCreateDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperDownloadDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperDownloadRequestDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.dto.question_paper.QuestionResponseDto;
import com.sciencematch.sciencematch.dto.question_paper.TeacherLevelUpdateDto;
import com.sciencematch.sciencematch.dto.question_paper.TeacherLevelResponseDto;
import com.sciencematch.sciencematch.dto.question_paper.WrongAnswerPeriodDto;
import com.sciencematch.sciencematch.dto.teacher.request.MultipleQuestionPaperSubmitDto;
import com.sciencematch.sciencematch.dto.teacher.request.QuestionPaperSubmitDto;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.QuestionPaperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @GetMapping("/level")
    @Operation(summary = "난이도 비율 불러오기")
    public ApiResponseDto<List<TeacherLevelResponseDto>> getTeacherLevel(
        @Parameter(hidden = true) @AuthenticationPrincipal User user
    ) {
        return ApiResponseDto.success(SuccessStatus.GET_TEACHER_LEVEL_SUCCESS,
            questionPaperService.getTeacherLevel(user.getUsername()));
    }

    @PostMapping("/level")
    @Operation(summary = "난이도 비율 저장하기")
    public ApiResponseDto<?> updateTeacherLevel(
        @RequestBody List<TeacherLevelUpdateDto> teacherLevelUpdateDtos
    ) {
        questionPaperService.updateTeacherLevel(teacherLevelUpdateDtos);
        return ApiResponseDto.success(SuccessStatus.UPDATE_TEACHER_LEVEL_SUCCESS);
    }

    @PostMapping("/questions/normal")
    @Operation(summary = "단원 유형별 학습지 범위 선택")
    public ApiResponseDto<List<QuestionResponseDto>> getNormalQuestions(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        NormalQuestionPaperRequestDto normalQuestionPaperRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_FOR_NORMAL_SUCCESS,
            questionPaperService.getNormalQuestions(user.getUsername(), normalQuestionPaperRequestDto));
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

    @PostMapping("/question-paper/wrong/assign")
    @Operation(summary = "학습지별 오답 문제 추출")
    public ApiResponseDto<List<QuestionResponseDto>> getWrongQuestionById(
        @RequestBody List<Long> assignQuestionIds) {
        return ApiResponseDto.success(SuccessStatus.GET_WRONG_SUCCESS,
            questionPaperService.getWrongQuestionById(assignQuestionIds));
    }

    @PostMapping("/question-paper/wrong/period")
    @Operation(summary = "기간별 오답 문제 추출")
    public ApiResponseDto<List<QuestionResponseDto>> getWrongQuestionByPeriod(
        @RequestBody WrongAnswerPeriodDto wrongAnswerPeriodDto) {
        return ApiResponseDto.success(SuccessStatus.GET_WRONG_SUCCESS,
            questionPaperService.getWrongQuestionByPeriod(wrongAnswerPeriodDto));
    }

    @GetMapping("/question-paper/download")
    @Operation(summary = "학습지 다운로드")
    public ApiResponseDto<QuestionPaperDownloadDto> downloadQuestionPaper(
        QuestionPaperDownloadRequestDto questionPaperDownloadRequestDto) {
        return ApiResponseDto.success(SuccessStatus.QUESTION_PAPER_DOWNLOAD_SUCCESS,
            questionPaperService.downloadQuestionPaper(questionPaperDownloadRequestDto));
    }
}
