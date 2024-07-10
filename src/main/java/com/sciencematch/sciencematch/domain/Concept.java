package com.sciencematch.sciencematch.domain;

import com.sciencematch.sciencematch.domain.common.AuditingTimeEntity;
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
public class Concept extends AuditingTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "concept_id")
    private Long id;
    @Setter
    private String image;
    private String blankImage;
    @OneToOne
    private Chapter chapter;


    @Builder
    public Concept(String image, String blankImage, Chapter chapter) {
        this.image = image;
        this.blankImage = blankImage;
        this.chapter = chapter;
    }
}
