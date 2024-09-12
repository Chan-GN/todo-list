package org.example.todolist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolist.security.CustomUserDetails;
import org.example.todolist.dto.todo.ToDoRequestDto;
import org.example.todolist.dto.todo.ToDoResponseDto;
import org.example.todolist.dto.todo.ToggleRequestDto;
import org.example.todolist.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/to-do")
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping
    public ResponseEntity<Long> addToDo(
            @RequestBody @Valid ToDoRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long savedToDoId = toDoService.saveToDo(dto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedToDoId);
    }

    @GetMapping
    public ResponseEntity<List<ToDoResponseDto>> getAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseEntity.ok(toDoService.findAll(customUserDetails.getMember().getId()));
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<Void> updateToDo(
            @PathVariable Long targetId,
            @RequestBody @Valid ToDoRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        toDoService.updateToDo(targetId, dto, customUserDetails.getMember().getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{targetId}/toggle")
    public ResponseEntity<Void> toggleToDoDone(
            @PathVariable Long targetId,
            @RequestBody @Valid ToggleRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        toDoService.toggleDone(targetId, dto, customUserDetails.getMember().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteOne(
            @PathVariable Long targetId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        toDoService.deleteToDo(targetId, customUserDetails.getMember().getId());
        return ResponseEntity.ok().build();
    }

}

