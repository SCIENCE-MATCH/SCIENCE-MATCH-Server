package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.csat.request.CsatIdsRequestDto;
import com.sciencematch.sciencematch.DTO.csat.response.CsatForNumberResponseDto;
import com.sciencematch.sciencematch.infrastructure.CsatRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsatService {

    private final CsatRepository csatRepository;

    public List<CsatForNumberResponseDto> getCsatIds(CsatIdsRequestDto csatIdsRequestDto) {
        return csatRepository.findAllBySubjectAndYearInAndMonthInAndPublisherIn(
                csatIdsRequestDto.getSubject(), csatIdsRequestDto.getYear(),
                csatIdsRequestDto.getMonth(), csatIdsRequestDto.getPublisher()).stream()
            .map(CsatForNumberResponseDto::of).collect(Collectors.toList());
    }
}
