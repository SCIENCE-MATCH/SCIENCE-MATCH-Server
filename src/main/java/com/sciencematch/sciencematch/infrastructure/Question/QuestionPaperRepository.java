package com.sciencematch.sciencematch.infrastructure.Question;

import com.sciencematch.sciencematch.domain.QuestionPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long>,
    QuestionPaperRepositoryCustom {

}
