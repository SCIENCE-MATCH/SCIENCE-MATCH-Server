package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.question.ConnectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectQuestionRepository extends JpaRepository<ConnectQuestion, Long> {

}
