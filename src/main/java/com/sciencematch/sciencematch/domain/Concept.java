package com.sciencematch.sciencematch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id") //join column은 자동으로 pk를 찾아 연결 (name은 말 그대로 내 테이블 내에서 저장할 컬럼의 이름)
    private Chapter chapter;

    @Builder
    public Concept(String image, Chapter chapter) {
        this.image = image;
        this.chapter = chapter;
        chapter.getConcepts().add(this);
    }
}
