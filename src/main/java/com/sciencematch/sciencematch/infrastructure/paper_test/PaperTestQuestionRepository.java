package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTestQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaperTestQuestionRepository extends JpaRepository<PaperTestQuestion, Long> {

    @Query("select pt from PaperTestQuestion pt where pt.paperTest = :paperTest")
    List<PaperTestQuestion> getAllPaperTestQuestionByPaperTest(@Param("paperTest") PaperTest paperTest);
}
