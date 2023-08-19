package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Group;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    default Group getGroupById(Long groupId) {
        return this.findById(groupId).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_GROUP_EXCEPTION,
                ErrorStatus.NOT_FOUND_GROUP_EXCEPTION.getMessage()));
    }
}
