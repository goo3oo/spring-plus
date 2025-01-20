package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.config.security.enrtity.CustomUserDetails;
import org.example.expert.domain.todo.dto.request.TodoGetRequest;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        return ResponseEntity.ok(todoService.saveTodo(userDetails, todoSaveRequest));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @ModelAttribute TodoGetRequest todoGetRequest) {
        return ResponseEntity.ok(todoService.getTodos(todoGetRequest));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @GetMapping("/todos/search")
    public ResponseEntity<Page<TodoSearchResponse>> searchTodos(
            @ModelAttribute TodoSearchRequest todoSearchRequest) {
        return ResponseEntity.ok(todoService.searchTodos(todoSearchRequest));
    }
}
