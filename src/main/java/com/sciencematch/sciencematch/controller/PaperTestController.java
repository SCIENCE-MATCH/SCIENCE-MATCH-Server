package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.MultiplePaperTestSubmitDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestRequestDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSelectDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSubmitDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.PaperTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "일대일질문", description = "일대일질문 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
@RequestMapping("/teacher")
public class PaperTestController {

    private final PaperTestService paperTestService;

    @PostMapping("/paper-test")
    @Operation(summary = "일대일 질문 조회")
    public ApiResponseDto<List<PaperTestResponseDto>> getAllQuestionPaper(
        PaperTestSelectDto paperTestSelectDto) {
        return ApiResponseDto.success(SuccessStatus.GET_PAPER_TEST_SUCCESS,
            paperTestService.getAllPaperTest(paperTestSelectDto));
    }

    @PostMapping("/paper-test/create")
    @Operation(summary = "일대일 질문 생성")
    public ApiResponseDto<?> createPaperTest(@RequestBody @Valid PaperTestRequestDto paperTestRequestDto) {
        paperTestService.createPaperTest(paperTestRequestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_PAPER_TEST_SUCCESS);
    }

    @PostMapping("/paper-test/submit")
    @Operation(summary = "단일 질문 출제")
    public ApiResponseDto<?> submitQuestionPaper(@RequestBody @Valid PaperTestSubmitDto paperTestSubmitDto) {
        paperTestService.submitPaperTest(paperTestSubmitDto);
        return ApiResponseDto.success(SuccessStatus.SUBMIT_PAPER_TEST_SUCCESS);
    }

    @PostMapping("/paper-test/multiple-submit")
    @Operation(summary = "복수 질문 출제")
    public ApiResponseDto<?> submitMultipleQuestionPaper(
        MultiplePaperTestSubmitDto multiplePaperTestSubmitDto) {
        paperTestService.submitMultiplePaperTest(multiplePaperTestSubmitDto);
        return ApiResponseDto.success(SuccessStatus.SUBMIT_PAPER_TEST_SUCCESS);
    }
}
