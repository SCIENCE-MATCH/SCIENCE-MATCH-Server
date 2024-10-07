package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.DTO.chapter.ChapterResponseDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatIdsRequestDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatRequestByNumDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatRequestDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatForNumberResponseDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatQuestionResponseDto;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.CsatService;
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
public class CsatController {

    private final CsatService csatService;

    //학습지 종류 및 범위선택에서 제공하기 위한 id들 반환
    @PostMapping("/ids")
    public ApiResponseDto<List<CsatForNumberResponseDto>> getCsatIds(
        @RequestBody CsatIdsRequestDto csatIdsRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_IDS_SUCCESS, csatService.getCsatIds(csatIdsRequestDto));
    }

    //문제번호로 추가
    @PostMapping("/question/num")
    public ApiResponseDto<List<CsatQuestionResponseDto>> getCsatQuestionByNum(
        @RequestBody List<CsatRequestByNumDto> csatRequestByNumDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_QUESTION_SUCCESS,
            csatService.getCsatQuestionByNum(csatRequestByNumDto));
    }

    //단원으로 추가시의 chapter 조회
    @GetMapping("/chapter/{subject}")
    public ApiResponseDto<List<ChapterResponseDto>> getChapter(@PathVariable(name = "subject") Subject subject) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_CHAPTER_SUCCESS,
            csatService.getChapter(subject));
    }

    //단원으로 추가
    @PostMapping("/question/num")
    public ApiResponseDto<List<CsatQuestionResponseDto>> getCsatQuestionByChapter(
        @RequestBody CsatRequestDto csatRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CSAT_QUESTION_SUCCESS,
            csatService.getCsatQuestionByChapter(csatRequestDto));
    }
}
