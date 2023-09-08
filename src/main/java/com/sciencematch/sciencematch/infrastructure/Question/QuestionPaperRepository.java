package com.sciencematch.sciencematch.infrastructure.Question;

import com.sciencematch.sciencematch.domain.QuestionPaper;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long>,
    QuestionPaperRepositoryCustom {

    default QuestionPaper getQuestionPaperById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION,
                ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION.getMessage()));
    }
}
