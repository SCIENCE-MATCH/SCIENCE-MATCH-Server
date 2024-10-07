package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.DTO.csat.request.CsatIdsRequestDto;
import com.sciencematch.sciencematch.DTO.csat.request.CsatRequestByNumDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatForNumberResponseDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatQuestionResponseDto;
import com.sciencematch.sciencematch.service.CsatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csat")
@RequiredArgsConstructor
public class CsatController {

    private final CsatService csatService;

    @PostMapping("/ids")
    public List<CsatForNumberResponseDto> getCsatIds(
        @RequestBody CsatIdsRequestDto csatIdsRequestDto) {
        return csatService.getCsatIds(csatIdsRequestDto);
    }

    @PostMapping("/question/num")
    public List<CsatQuestionResponseDto> getCsatQuestionByNum(
        @RequestBody List<CsatRequestByNumDto> csatRequestByNumDto) {
        return csatService.getCsatQuestionByNum(csatRequestByNumDto);
    }
}
