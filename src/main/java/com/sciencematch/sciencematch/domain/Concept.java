package com.sciencematch.sciencematch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Concept {

    @Id
    @GeneratedValue
    @Column(name = "concept_id")
    private Long id;
    private String image;
    @OneToOne
    private Chapter chapter;


    @Builder
    public Concept(String image, Chapter chapter) {
        this.image = image;
        this.chapter = chapter;
    }
}
