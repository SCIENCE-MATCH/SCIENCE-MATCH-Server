package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignQuestionRepository extends JpaRepository<AssignQuestions, Long> {

    @EntityGraph(attributePaths = {"questionPaper"})
    List<AssignQuestions> findAllByStudent(Student student);

    @EntityGraph(attributePaths = {"questionPaper", "student"})
    @Query("select aq from AssignQuestions aq where aq.student.id = :studentId")
    List<AssignQuestions> findAllByStudentId(@Param("studentId") Long studentId);

    default AssignQuestions getAssignQuestionsById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION,
                ErrorStatus.NOT_FOUND_QUESTION_PAPER_EXCEPTION.getMessage()));
    }

    void deleteAllByQuestionPaperId(Long id);

    @EntityGraph(attributePaths = {"questionPaper"})
    @Query("SELECT a FROM AssignQuestions a WHERE a.student.id = :studentId AND a.updatedAt BETWEEN :startDate AND :endDate AND (a.assignStatus = :status1 OR a.assignStatus = :status2)")
    List<AssignQuestions> findAllForSummary(
        @Param("studentId") Long studentId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("status1") AssignStatus status1,
        @Param("status2") AssignStatus status2
    );
}
