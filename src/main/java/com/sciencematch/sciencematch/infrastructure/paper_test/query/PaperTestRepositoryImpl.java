package com.sciencematch.sciencematch.infrastructure.paper_test.query;

import static com.sciencematch.sciencematch.domain.paper_test.QPaperTest.paperTest;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sciencematch.sciencematch.dto.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.dto.paper_test.PaperTestSelectDto;
import com.sciencematch.sciencematch.dto.paper_test.QPaperTestResponseDto;
import com.sciencematch.sciencematch.enums.School;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

public class PaperTestRepositoryImpl implements PaperTestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PaperTestRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PaperTestResponseDto> search(PaperTestSelectDto paperTestSelectDto) {
        return queryFactory
            .select(new QPaperTestResponseDto(
                paperTest.id,
                paperTest.school,
                paperTest.semester,
                paperTest.image,
                paperTest.question,
                paperTest.makerName,
                paperTest.subject,
                paperTest.chapterDescription,
                paperTest.createAt
            ))
            .from(paperTest)
            .where(schoolEq(paperTestSelectDto.getSchool()),
                dateGoe(paperTestSelectDto.getStart()),
                dateLoe(paperTestSelectDto.getEnd()))
            .fetch();
    }

    private BooleanExpression schoolEq(School school) {
        return school == null ? null : paperTest.school.eq(school);
    }

    private BooleanExpression dateGoe(LocalDateTime start) {
        return start == null ? null : paperTest.createAt.goe(start);
    }

    private BooleanExpression dateLoe(LocalDateTime end) {
        return end == null ? null : paperTest.createAt.loe(end);
    }
}
