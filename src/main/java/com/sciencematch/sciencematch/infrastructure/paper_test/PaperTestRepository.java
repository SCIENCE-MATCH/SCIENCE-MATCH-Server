package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.sciencematch.sciencematch.domain.paper_test.PaperTest;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import com.sciencematch.sciencematch.infrastructure.paper_test.query.PaperTestRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaperTestRepository extends JpaRepository<PaperTest, Long>,
    PaperTestRepositoryCustom {

    default PaperTest getPaperTestById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_PAPER_TEST_EXCEPTION,
                ErrorStatus.NOT_FOUND_PAPER_TEST_EXCEPTION.getMessage()));
    }

    @Query("select pt from PaperTest pt where pt.id in :paperTestIds")
    List<PaperTest> getPaperTestsByList(@Param("paperTestIds") List<Long> paperTestIds);
}
