package com.sciencematch.sciencematch.dto.csat.request;

import java.util.List;
import lombok.Data;

@Data
public class CsatRequestByNumDto {

    private Long csatId;
    private List<Double> pageOrder;
    private List<Integer> score;

}
