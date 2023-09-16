package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignPaperTestRepository extends JpaRepository<AssignPaperTest, Long> {

    @EntityGraph(attributePaths = {"paperTest"})
    List<AssignPaperTest> findAllByStudent(Student student);

}
