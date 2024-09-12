package org.example.todolist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.todolist.dto.to_do.ToDoRequestDto;
import org.example.todolist.dto.to_do.ToDoResponseDto;
import org.example.todolist.dto.to_do.ToggleRequestDto;
import org.example.todolist.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/to-do")
public class ToDoController {

    private final ToDoService toDoService;

    @PostMapping
    public ResponseEntity<Void> addToDo(@RequestBody @Valid ToDoRequestDto dto) {
        toDoService.saveToDo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ToDoResponseDto>> getAll() {
        return ResponseEntity.ok(toDoService.findAll());
    }

    @PutMapping("/{targetId}")
    public ResponseEntity<Void> updateToDo(@PathVariable Long targetId, @RequestBody @Valid ToDoRequestDto dto) {
        toDoService.updateToDo(targetId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{targetId}/toggle")
    public ResponseEntity<Void> toggleToDoDone(@PathVariable Long targetId, @RequestBody @Valid ToggleRequestDto dto) {
        toDoService.toggleDone(targetId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long targetId) {
        toDoService.deleteToDo(targetId);
        return ResponseEntity.ok().build();
    }

}

