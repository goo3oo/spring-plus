package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryQuerydsl {

    @Query("SELECT t FROM Todo t LEFT JOIN t.user u " +
            "WHERE (:startDate IS NULL OR t.modifiedAt >= :startDate) " +
            "AND (:endDate IS NULL OR t.modifiedAt <= :endDate) " +
            "AND (:weather IS NULL OR t.weather LIKE CONCAT('%', :weather, '%')) " +
            "ORDER BY t.modifiedAt DESC"
    )
    Page<Todo> findAllByOrderByModifiedAtDesc(
            Pageable pageable,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("weather") String weather
    );
}
