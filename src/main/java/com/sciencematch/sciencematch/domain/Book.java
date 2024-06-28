package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private School school;
    private Semester semester;
    private String title;
    private Integer editionNum;
    private String publisher;

    @Builder
    public Book(School school, Semester semester, String title, Integer editionNum, String publisher) {
        this.school = school;
        this.semester = semester;
        this.title = title;
        this.editionNum = editionNum;
        this.publisher = publisher;
    }

}
