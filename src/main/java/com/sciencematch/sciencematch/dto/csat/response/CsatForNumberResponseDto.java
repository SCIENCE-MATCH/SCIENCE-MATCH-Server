package com.sciencematch.sciencematch.dto.csat.response;

import com.sciencematch.sciencematch.enums.Subject;
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
