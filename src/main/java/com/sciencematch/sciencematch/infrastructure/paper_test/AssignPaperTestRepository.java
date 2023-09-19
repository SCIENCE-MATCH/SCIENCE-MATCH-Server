package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignPaperTestRepository extends JpaRepository<AssignPaperTest, Long> {

    @EntityGraph(attributePaths = {"paperTest"})
    List<AssignPaperTest> findAllByStudent(Student student);

    @EntityGraph(attributePaths = {"paperTest", "student"})
    @Query("select ap from AssignPaperTest ap where ap.student.id = :studentId")
    List<AssignPaperTest> findAllByStudentId(@Param("studentId") Long studentId);

    default AssignPaperTest getAssignPaperTestById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_PAPER_TEST_EXCEPTION,
                ErrorStatus.NOT_FOUND_PAPER_TEST_EXCEPTION.getMessage()));
    }

}
