package com.sciencematch.sciencematch.dto.csat.request;

import java.util.List;
import lombok.Data;

@Data
public class CsatRequestByChapterDto {

    private List<Long> csatId;
    private List<Long> chapterId;
    private List<Integer> score;

}
