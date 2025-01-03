package com.sciencematch.sciencematch.dto.csat.response;

import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.Csat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CsatAdminResponseDto {

    private Long csatId;
    private Subject subject;
    private Integer year;
    private Integer month;
    private Integer subjectNum;
    private String publisher;

    public static CsatAdminResponseDto of(Csat csat) {
        return new CsatAdminResponseDto(csat.getId(), csat.getSubject(), csat.getYear(),
            csat.getMonth(), csat.getSubjectNum(), csat.getPublisher());
    }

}
