package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TodoSearchRequest {

    private Integer page;
    private Integer size;
    private String keyword;
    private LocalDate startDate;
    private LocalDate endDate;
    private String nickName;

    public Integer getPage() {
        return page != null ? page : 1;
    }

    public Integer getSize() {
        return size != null ? size : 10;
    }

    public LocalDateTime getStartDate() {
        return startDate != null ? startDate.atStartOfDay() : null;
    }

    public LocalDateTime getEndDate() {
        return endDate != null ? endDate.atTime(LocalTime.MAX) : null;
    }
}
