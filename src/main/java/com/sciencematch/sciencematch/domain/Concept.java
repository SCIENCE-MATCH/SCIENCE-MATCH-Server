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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Concept {

    @Id
    @GeneratedValue
    @Column(name = "concept_id")
    private Long id;
    @Setter
    private String image;
    @OneToOne
    private Chapter chapter;


    @Builder
    public Concept(String image, Chapter chapter) {
        this.image = image;
        this.chapter = chapter;
    }
}
