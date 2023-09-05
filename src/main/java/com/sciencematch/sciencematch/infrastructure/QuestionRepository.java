package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Question;
import com.sciencematch.sciencematch.domain.enumerate.Category;
import com.sciencematch.sciencematch.domain.enumerate.Level;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q where q.chapter in :chapter and q.level = :level and q.category = :category")
    List<Question> findAllByChapters(@Param("chapter") List<Chapter> chapterList,
        @Param("level") Level level, @Param("category") Category category);

}
