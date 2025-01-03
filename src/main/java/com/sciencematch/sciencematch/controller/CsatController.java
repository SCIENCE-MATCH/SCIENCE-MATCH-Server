package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.dto.chapter.ChapterResponseDto;
import com.sciencematch.sciencematch.dto.csat.request.CsatIdsRequestDto;
import com.sciencematch.sciencematch.dto.csat.request.CsatRequestByNumDto;
import com.sciencematch.sciencematch.dto.csat.request.CsatRequestByChapterDto;
import com.sciencematch.sciencematch.dto.csat.response.CsatForNumberResponseDto;
import com.sciencematch.sciencematch.dto.csat.response.CsatQuestionResponseDto;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.CsatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csat")
@RequiredArgsConstructor
@Tag(name = "모의고사", description = "모의고사 API")
public class CsatController {

    private final CsatService csatService;

    //학습지 종류 및 범위선택에서 제공하기 위한 id들 반환
    @PostMapping("/ids")
    @Operation(summary = "모의고사 id 조회")
    public ApiResponseDto<List<CsatForNumberResponseDto>> getCsatIds(
        @RequestBody CsatIdsRequestDto csatIdsRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_IDS_SUCCESS, csatService.getCsatIds(csatIdsRequestDto));
    }

    //문제번호로 추가
    @PostMapping("/question/num")
    @Operation(summary = "문제 번호로 문제 생성")
    public ApiResponseDto<List<CsatQuestionResponseDto>> getCsatQuestionByNum(
        @RequestBody List<CsatRequestByNumDto> csatRequestByNumDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_QUESTION_SUCCESS,
            csatService.getCsatQuestionByNum(csatRequestByNumDto));
    }

    //단원으로 추가시의 chapter 조회
    @GetMapping("/chapter/{subject}")
    @Operation(summary = "모의고사 단원 조회")
    public ApiResponseDto<List<ChapterResponseDto>> getChapter(@PathVariable(name = "subject") Subject subject) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_CHAPTER_SUCCESS,
            csatService.getChapter(subject));
    }

    //단원으로 추가
    @PostMapping("/question/chapter")
    @Operation(summary = "단원으로 문제 생성")
    public ApiResponseDto<List<CsatQuestionResponseDto>> getCsatQuestionByChapter(
        @RequestBody CsatRequestByChapterDto csatRequestByChapterDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_QUESTION_SUCCESS,
            csatService.getCsatQuestionByChapter(csatRequestByChapterDto));
    }
}
