package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.enumerate.Grade;
import com.sciencematch.sciencematch.domain.enumerate.School;
import com.sciencematch.sciencematch.domain.enumerate.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("select c from Chapter c where c.parent is null and c.school = :school and c.grade = :grade and c.subject = :subject order by c.listOrder")
    List<Chapter> getChapterList(@Param("school") School school, @Param("grade") Grade grade,
        @Param("subject") Subject subject);
}
