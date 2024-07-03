package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    default Answer getAnswerById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_ANSWER_EXCEPTION,
                ErrorStatus.NOT_FOUND_ANSWER_EXCEPTION.getMessage()));
    }

    @Query("select a from Answer a where a.assignQuestions.id in :ids and a.rightAnswer = false")
    List<Answer> findAllByAssignQuestionsId(@Param("ids") List<Long> ids);

    List<Answer> findAllByRightAnswerAndUpdatedAtBetween(Boolean rightAnswer, LocalDateTime start, LocalDateTime end);
}
