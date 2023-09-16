package com.sciencematch.sciencematch.infrastructure.paper_test;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestSelectDto;
import com.sciencematch.sciencematch.DTO.paper_test.QPaperTestResponseDto;
import com.sciencematch.sciencematch.Enums.School;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

import static com.sciencematch.sciencematch.domain.paper_test.QPaperTest.paperTest;

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
                paperTest.title,
                paperTest.makerName,
                paperTest.subject,
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
