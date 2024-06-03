package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

}
