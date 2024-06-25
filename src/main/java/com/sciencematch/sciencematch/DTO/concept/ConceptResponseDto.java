package com.sciencematch.sciencematch.DTO.concept;

import com.sciencematch.sciencematch.domain.Concept;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConceptResponseDto {
    private Long id;
    private String url;

    public static ConceptResponseDto of(Concept concept) {
        return new ConceptResponseDto(concept.getId(), concept.getImage());
    }

}
