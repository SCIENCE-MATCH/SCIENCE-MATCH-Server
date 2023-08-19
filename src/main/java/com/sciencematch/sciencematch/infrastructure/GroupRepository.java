package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Groups;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Long> {

    default Groups getGroupById(Long groupId) {
        return this.findById(groupId).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_GROUP_EXCEPTION,
                ErrorStatus.NOT_FOUND_GROUP_EXCEPTION.getMessage()));
    }
}
