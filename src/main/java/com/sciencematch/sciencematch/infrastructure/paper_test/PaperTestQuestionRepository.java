package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.sciencematch.sciencematch.domain.paper_test.PaperTestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperTestQuestionRepository extends JpaRepository<PaperTestQuestion, Long> {

}
