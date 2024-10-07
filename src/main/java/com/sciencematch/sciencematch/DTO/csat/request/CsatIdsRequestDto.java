package com.sciencematch.sciencematch.DTO.csat.request;

import com.sciencematch.sciencematch.Enums.Subject;
import java.util.List;
import lombok.Data;

@Data
public class CsatIdsRequestDto {

    private Subject subject; // 단일
    private List<Integer> year;
    private List<Integer> month;
    private List<String> publisher;

}
