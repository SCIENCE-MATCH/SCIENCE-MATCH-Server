package com.sciencematch.sciencematch.dto.csat.request;

import com.sciencematch.sciencematch.enums.Subject;
import lombok.Data;

@Data
public class CsatRequestDto {

    private Integer year;
    private Integer month;
    private Subject subject;
    private Integer subjectNum;
    private String publisher;

}
