package org.example.expert.domain.manager.log;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long todoId;

    @Column(nullable = false)
    private Long managerUserId;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    private LocalDateTime createAt;
}
