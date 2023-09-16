package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.question.ConnectQuestion;
import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConnectQuestionRepository extends JpaRepository<ConnectQuestion, Long> {

    @EntityGraph(attributePaths = {"question"})
    @Query("select cq from ConnectQuestion cq where cq.questionPaper = :questionPaper")
    List<ConnectQuestion> getAllConnectQuestionByQuestionPaper(QuestionPaper questionPaper);

}
