package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.Enums.Level;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.TeacherLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherLevelRepository extends JpaRepository<TeacherLevel, Long> {

    TeacherLevel findByTeacherAndLevel(Teacher teacher, Level level);
}
