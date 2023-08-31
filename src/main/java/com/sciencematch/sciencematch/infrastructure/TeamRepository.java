package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Team;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    default Team getTeamById(Long teamId) {
        return this.findById(teamId).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_GROUP_EXCEPTION,
                ErrorStatus.NOT_FOUND_GROUP_EXCEPTION.getMessage()));
    }

    @Override
    @EntityGraph(attributePaths = {"teacher"})
    List<Team> findAll();
}
