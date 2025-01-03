package com.sciencematch.sciencematch.dto.concept;

import com.sciencematch.sciencematch.domain.Concept;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConceptResponseDto {
    private Long id;
    private String image;
    private String blankImage;
    private Long chapterId;

    public static ConceptResponseDto of(Concept concept) {
        return new ConceptResponseDto(concept.getId(), concept.getImage(), concept.getBlankImage(), concept.getChapter().getId());
    }

}
