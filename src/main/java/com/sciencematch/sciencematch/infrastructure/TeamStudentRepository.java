package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.TeamStudent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamStudentRepository extends JpaRepository<TeamStudent, Long> {

}
