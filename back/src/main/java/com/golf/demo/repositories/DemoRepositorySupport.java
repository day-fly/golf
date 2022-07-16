package com.golf.demo.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.golf.demo.Demo;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.golf.demo.QDemo.demo;

@Repository
public class DemoRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param queryFactory must not be {@literal null}.
     */
    public DemoRepositorySupport(JPAQueryFactory queryFactory) {
        super(Demo.class);
        this.queryFactory = queryFactory;
    }

    public List<Demo> getDemoes() {
        return queryFactory
                .selectFrom(demo)
                .fetch();
    }

}
