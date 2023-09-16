package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignQuestionRepository extends JpaRepository<AssignQuestions, Long> {

    @EntityGraph(attributePaths = {"questionPaper"})
    List<AssignQuestions> findAllByStudent(Student student);

    default AssignQuestions getAssignQuestionsById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION,
                ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION.getMessage()));
    }
}
