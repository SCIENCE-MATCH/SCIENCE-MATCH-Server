package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    default Answer getAnswerById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_ANSWER_EXCEPTION,
                ErrorStatus.NOT_FOUND_ANSWER_EXCEPTION.getMessage()));
    }
}
