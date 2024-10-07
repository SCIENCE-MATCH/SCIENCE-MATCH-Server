package com.sciencematch.sciencematch.DTO.csat.request;

import com.sciencematch.sciencematch.Enums.Subject;
import lombok.Data;

@Data
public class CsatRequestDto {

    private Integer year;
    private Integer month;
    private Subject subject;
    private Integer subjectNum;
    private String publisher;

}
