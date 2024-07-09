package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.TeacherLevel;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.CustomException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherLevelRepository extends JpaRepository<TeacherLevel, Long> {

    TeacherLevel findByTeacherAndLevel(Teacher teacher, Level level);

    List<TeacherLevel> findAllByTeacher(Teacher teacher);

    default TeacherLevel getTeacherLevelById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorStatus.NOT_FOUND_USER_EXCEPTION,
                ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }
}
