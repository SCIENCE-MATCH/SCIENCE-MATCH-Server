package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Concept;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConceptRepository extends JpaRepository<Concept, Long> {

    @Query("select c from Concept c where c.chapter.id = :chapterId")
    Concept getByChapterId(@Param("chapterId") Long id);

    @Query("select c from Concept c where c.chapter.id in :ids")
    List<Concept> getByChapterIds(@Param("ids") List<Long> ids);
}
