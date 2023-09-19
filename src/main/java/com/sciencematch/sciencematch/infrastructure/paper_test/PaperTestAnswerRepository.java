package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.sciencematch.sciencematch.domain.paper_test.PaperTestAnswer;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperTestAnswerRepository extends JpaRepository<PaperTestAnswer, Long> {

    default PaperTestAnswer getAnswerById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_ANSWER_EXCEPTION,
                ErrorStatus.NOT_FOUND_ANSWER_EXCEPTION.getMessage()));
    }
}
