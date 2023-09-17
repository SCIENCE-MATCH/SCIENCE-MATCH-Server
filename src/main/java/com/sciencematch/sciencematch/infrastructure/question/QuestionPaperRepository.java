package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import com.sciencematch.sciencematch.infrastructure.question.query.QuestionPaperRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long>,
    QuestionPaperRepositoryCustom {

    default QuestionPaper getQuestionPaperById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION,
                ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION.getMessage()));
    }

    @Query("select qp from QuestionPaper qp where qp.id in :questionPaperIds")
    List<QuestionPaper> getQuestionPapersByList(@Param("questionPaperIds") List<Long> questionPaperIds);
}
