package com.sciencematch.sciencematch.DTO.csat.response;

import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.Csat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CsatForNumberResponseDto {

    private Long csatId;
    private Subject subject;
    private Integer year;
    private Integer month;
    private String publisher;
    private Integer subjectNum;

    public static CsatForNumberResponseDto of(Csat csat) {
        return new CsatForNumberResponseDto(csat.getId(), csat.getSubject(), csat.getYear(),
            csat.getMonth(), csat.getPublisher(), csat.getSubjectNum());
    }

}
