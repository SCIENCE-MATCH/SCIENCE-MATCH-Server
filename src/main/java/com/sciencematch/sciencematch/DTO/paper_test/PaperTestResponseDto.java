package com.sciencematch.sciencematch.DTO.paper_test;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaperTestResponseDto {

    private Long id;
    private School school;
    private String question;
    private String makerName;
    private Subject subject;
    private LocalDateTime createdAt;

    @QueryProjection
    public PaperTestResponseDto(Long id, School school,
        String question, String makerName, Subject subject, LocalDateTime createdAt) {
        this.id = id;
        this.school = school;
        this.question = question;
        this.makerName = makerName;
        this.subject = subject;
        this.createdAt = createdAt;
    }

    public static PaperTestResponseDto of(PaperTest paperTest) {
        return new PaperTestResponseDto(paperTest.getId(), paperTest.getSchool(),
            paperTest.getQuestion(), paperTest.getMakerName(), paperTest.getSubject(),
            paperTest.getCreateAt());
    }

}
