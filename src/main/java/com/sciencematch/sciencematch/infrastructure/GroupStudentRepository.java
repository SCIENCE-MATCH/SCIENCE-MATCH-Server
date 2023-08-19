package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.GroupStudent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {

}
