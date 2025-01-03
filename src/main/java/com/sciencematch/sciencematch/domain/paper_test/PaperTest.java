package com.sciencematch.sciencematch.domain.paper_test;

import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Semester;
import com.sciencematch.sciencematch.enums.Subject;
import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaperTest extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "paper_test_id")
    private Long id;

    //1대1 질문지 조회용 데이터
    private School school;
    private Semester semester;
    private Subject subject;
    private String chapterDescription;
    private String makerName;

    private String image;
    private String question;
    private String solution;

    @OneToMany(mappedBy = "paperTest", cascade = {CascadeType.REMOVE})
    private final List<AssignPaperTest> assignPaperTests = new ArrayList<>();

    @Builder
    public PaperTest(School school, Semester semester, Subject subject, String chapterDescription, String makerName, String image, String question, String solution) {
        this.school = school;
        this.semester = semester;
        this.subject = subject;
        this.chapterDescription = chapterDescription;
        this.makerName = makerName;
        this.image = image;
        this.question = question;
        this.solution = solution;
    }
}
