package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Admin;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    default Admin getAdminById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_USER_EXCEPTION,
                ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }
}
