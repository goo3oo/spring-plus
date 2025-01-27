package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TodoGetRequest {

    private Integer page;
    private Integer size;
    private String weather;
    private LocalDate startDate;
    private LocalDate endDate;

    // NPE 방지를 위해 기본값 설정
    public Integer getPage() {
        return page != null ? page : 1;
    }

    public Integer getSize() {
        return size != null ? size : 10;
    }

    // LocalDate -> LocalDateTime
    public LocalDateTime getStartDate() {
        return startDate != null ? startDate.atStartOfDay() : null;
    }

    public LocalDateTime getEndDate() {
        return endDate != null ? endDate.atTime(LocalTime.MAX) : null;
    }
}