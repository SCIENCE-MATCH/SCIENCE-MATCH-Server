package com.sciencematch.sciencematch.infrastructure.question;

import com.sciencematch.sciencematch.enums.Category;
import com.sciencematch.sciencematch.enums.Level;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import com.sciencematch.sciencematch.infrastructure.question.query.QuestionRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

    public interface QuestionRepository extends JpaRepository<Question, Long>,
    QuestionRepositoryCustom {

    @Query("select q from Question q where q.chapterId in :chapterIds and q.level = :level and q.category = :category")
    List<Question> findAllByChapterIds(@Param("chapterIds") List<Long> chapterIds,
        @Param("level") Level level, @Param("category") Category category);

    @Query("select q from Question q where q.id in :ids")
    List<Question> findAllByIds(@Param("ids") List<Long> idList);

    List<Question> findAllByChapterId(Long chapterId);

    List<Question> findAllByBookId(Long bookId);

    List<Question> findAllByBookIdAndPage(Long bookId, Integer page);

    List<Question> findAllByCsatIdIn(List<Long> csatId);

    default Question getQuestionById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_QUESTION_EXCEPTION,
                ErrorStatus.NOT_FOUND_QUESTION_EXCEPTION.getMessage()));
    }

}
