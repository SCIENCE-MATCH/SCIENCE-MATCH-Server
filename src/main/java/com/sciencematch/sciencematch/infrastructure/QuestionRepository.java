package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
