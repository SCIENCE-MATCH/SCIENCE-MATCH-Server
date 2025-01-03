package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.enums.Authority;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByEmail(String email);

    default Teacher getTeacherByEmail(String email) {
        return this.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    default Teacher getTeacherById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_USER_EXCEPTION,
                ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    Optional<Teacher> findByEmail(String email);

    List<Teacher> findAllByAuthority(Authority authority);
}
