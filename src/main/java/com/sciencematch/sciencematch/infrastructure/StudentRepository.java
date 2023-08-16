package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByPhoneNum(String phoneNum);

    Optional<Student> findByPhoneNum(String PhoneNum);

    default Student getStudentsByPhoneNum(String phoneNum) {
        return this.findByPhoneNum(phoneNum)
            .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

}
