package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.domain.question.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
