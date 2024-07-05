package com.sciencematch.sciencematch.DTO.paper_test;

import com.querydsl.core.annotations.QueryProjection;
import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PaperTestResponseDto {

    private Long id;
    private School school;
    private Semester semester;
    private String image;
    private String question;
    private String makerName;
    private Subject subject;
    private String chapterDescription;
    private LocalDateTime createdAt;

    @QueryProjection
    public PaperTestResponseDto(Long id, School school, Semester semester, String image,
        String question, String makerName, Subject subject, String chapterDescription, LocalDateTime createdAt) {
        this.id = id;
        this.school = school;
        this.semester = semester;
        this.image = image;
        this.question = question;
        this.makerName = makerName;
        this.subject = subject;
        this.chapterDescription = chapterDescription;
        this.createdAt = createdAt;
    }

    public static PaperTestResponseDto of(PaperTest paperTest) {
        return new PaperTestResponseDto(paperTest.getId(), paperTest.getSchool(), paperTest.getSemester(), paperTest.getImage(),
            paperTest.getQuestion(), paperTest.getMakerName(), paperTest.getSubject(), paperTest.getChapterDescription(),
            paperTest.getCreateAt());
    }

}
