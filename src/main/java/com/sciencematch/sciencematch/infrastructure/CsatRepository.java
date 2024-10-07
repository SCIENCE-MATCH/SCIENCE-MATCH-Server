package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.Enums.Subject;
import com.sciencematch.sciencematch.domain.Csat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsatRepository extends JpaRepository<Csat, Long> {

    List<Csat> findAllBySubjectAndYearAndMonth(Subject subject, Integer year, Integer month);

    List<Csat> findAllBySubjectAndYearInAndMonthInAndPublisherIn(Subject subject,
        List<Integer> year, List<Integer> month, List<String> publisher);

}
