package com.sciencematch.sciencematch.infrastructure.question.query;

import static com.sciencematch.sciencematch.domain.question.QQuestion.question;
import static com.sciencematch.sciencematch.domain.QChapter.chapter;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sciencematch.sciencematch.DTO.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.DTO.question_paper.QQuestionResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionResponseDto;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import java.util.List;
import javax.persistence.EntityManager;

public class QuestionRepositoryImpl implements QuestionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<QuestionResponseDto> search(
        NormalQuestionPaperRequestDto normalQuestionPaperRequestDto) {
        return queryFactory
            .select(new QQuestionResponseDto(
                question.id,
                question.image,
                question.category,
                question.level,
                question.score,
                chapter.id,
                chapter.description
            )).from(question)
            .join(chapter).on(question.chapterId.eq(chapter.id))
            .where(chaptersEq(normalQuestionPaperRequestDto.getChapterIds()),
                categoryEq(normalQuestionPaperRequestDto.getCategory()),
                mockExamEq(normalQuestionPaperRequestDto.getMockExam()))
            .fetch();
    }

    private BooleanExpression chaptersEq(List<Long> chaptersId) {
        return chaptersId.isEmpty() ? null : question.chapterId.in(chaptersId);
    }

    private BooleanExpression categoryEq(List<Category> category) {
        return category.isEmpty() ? null : question.category.in(category);
    }

    private BooleanExpression mockExamEq(Boolean mockExam) {
        if (mockExam == null) {
            return question.questionTag.eq(QuestionTag.MOCK_EXAM);
        }
        return mockExam ? question.questionTag.ne(QuestionTag.TEXT_BOOK)
            : question.questionTag.eq(QuestionTag.NORMAL);
    }
}
