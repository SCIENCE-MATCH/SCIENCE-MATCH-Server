package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignPaperTestRepository extends JpaRepository<AssignPaperTest, Long> {

    @EntityGraph(attributePaths = {"paperTest"})
    List<AssignPaperTest> findAllByStudentAndAssignStatus(Student student, AssignStatus assignStatus);

    @EntityGraph(attributePaths = {"paperTest", "student"})
    @Query("select ap from AssignPaperTest ap where ap.student.id = :studentId")
    List<AssignPaperTest> findAllByStudentId(@Param("studentId") Long studentId);

    default AssignPaperTest getAssignPaperTestById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_PAPER_TEST_EXCEPTION,
                ErrorStatus.NOT_FOUND_PAPER_TEST_EXCEPTION.getMessage()));
    }

    void deleteAllByPaperTestId(Long paperTestId);

    @EntityGraph(attributePaths = {"paperTest"})
    @Query("SELECT a FROM AssignPaperTest a WHERE a.student.id = :studentId AND a.updatedAt BETWEEN :startDate AND :endDate AND (a.assignStatus = :status1 OR a.assignStatus = :status2)")
    List<AssignPaperTest> findAllForSummary(
        @Param("studentId") Long studentId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("status1") AssignStatus status1,
        @Param("status2") AssignStatus status2
    );
}
