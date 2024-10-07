package com.sciencematch.sciencematch.DTO.csat.request;

import java.util.List;
import lombok.Data;

@Data
public class CsatRequestDto {

    private List<Long> csatId;
    private List<Long> chapterId;
    private List<Integer> score;

}
