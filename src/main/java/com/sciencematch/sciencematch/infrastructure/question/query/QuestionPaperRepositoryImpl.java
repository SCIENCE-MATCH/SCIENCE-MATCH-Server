package com.sciencematch.sciencematch.infrastructure.question.query;

import static com.sciencematch.sciencematch.domain.question.QQuestionPaper.questionPaper;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sciencematch.sciencematch.DTO.question_paper.QQuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.Enums.QuestionTag;
import com.sciencematch.sciencematch.Enums.School;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

public class QuestionPaperRepositoryImpl implements QuestionPaperRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestionPaperRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    //학년, 학습지 유형, 생성 기간
    public List<QuestionPaperResponseDto> search(QuestionPaperSelectDto questionPaperSelectDto) {
        return queryFactory
            .select(new QQuestionPaperResponseDto(
                questionPaper.id,
                questionPaper.school,
                questionPaper.questionNum,
                questionPaper.title,
                questionPaper.makerName,
                questionPaper.category,
                questionPaper.subject,
                questionPaper.pdf,
                questionPaper.createAt))
            .from(questionPaper)
            .where(schoolEq(questionPaperSelectDto.getSchool()),
                questionTagEq(questionPaperSelectDto.getQuestionTag()),
                dateGoe(questionPaperSelectDto.getStart()),
                dateLoe(questionPaperSelectDto.getEnd()))
            .fetch();
    }
    private BooleanExpression schoolEq(School school) {
        return school == null ? null : questionPaper.school.eq(school);
    }

    private BooleanExpression questionTagEq(QuestionTag questionTag) {
        return questionTag == null ? null : questionPaper.questionTag.eq(questionTag);
    }

    private BooleanExpression dateGoe(LocalDateTime start) {
        return start == null ? null : questionPaper.createAt.goe(start);
    }

    private BooleanExpression dateLoe(LocalDateTime end) {
        return end == null ? null : questionPaper.createAt.loe(end);
    }
}
