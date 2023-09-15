package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignQuestionRepository extends JpaRepository<AssignQuestions, Long> {

}
