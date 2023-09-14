package com.sciencematch.sciencematch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignPaperTest {

    @Id
    @GeneratedValue
    @Column(name = "assign_paper_test_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paper_test_id")
    private PaperTest paperTest;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
