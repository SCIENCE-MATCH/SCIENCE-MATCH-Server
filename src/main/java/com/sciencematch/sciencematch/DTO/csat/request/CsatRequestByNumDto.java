package com.sciencematch.sciencematch.DTO.csat.request;

import java.util.List;
import lombok.Data;

@Data
public class CsatRequestByNumDto {

    private Long csatId;
    private List<Double> pageOrder;
    private List<Integer> score;

}
