package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignQuestionRepository extends JpaRepository<AssignQuestions, Long> {

    @EntityGraph(attributePaths = {"questionPaper"})
    List<AssignQuestions> findAllByStudent(Student student);
}
