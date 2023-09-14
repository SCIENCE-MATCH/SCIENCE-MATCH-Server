package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Semester;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
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
    private Long chapterId;

    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PaperTestQuestion> questions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paperTest")
    private List<AssignPaperTest> assignPaperTests = new ArrayList<>();
}
