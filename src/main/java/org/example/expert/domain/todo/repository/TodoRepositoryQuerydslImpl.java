package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryQuerydslImpl implements TodoRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(todo)
                        .innerJoin(todo.user, user).fetchJoin()
                        .where(todo.id.eq(todoId))
                        .fetchOne()
        );
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(
            String keyword,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String nickName,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.isEmpty()) {
            builder.and(todo.title.containsIgnoreCase(keyword));
        }
        if (startDate != null) {
            builder.and(todo.createdAt.goe(startDate));
        }
        if (endDate != null) {
            builder.and(todo.createdAt.loe(endDate));
        }
        if (nickName != null && !nickName.isEmpty()) {
            builder.and(todo.managers.any().user.nickName.containsIgnoreCase(nickName));
        }

        List<TodoSearchResponse> results = queryFactory.select(
                        Projections.constructor(
                                TodoSearchResponse.class,
                                todo.title,
                                todo.managers.size().longValue(),
                                todo.comments.size().longValue()
                        ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .leftJoin(todo.comments, comment)
                .where(builder)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();
        total = (total == null) ? 0L : total;

        return new PageImpl<>(results, pageable, total);
    }
}
