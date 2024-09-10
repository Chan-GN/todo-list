package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.ToDo;
import org.example.todolist.dto.ToDoRequestDto;
import org.example.todolist.dto.ToDoResponseDto;
import org.example.todolist.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Transactional
    public void saveToDo(ToDoRequestDto dto) {
        ToDo toDo = ToDo.of(dto.getContent());
        toDoRepository.save(toDo);
    }

    public List<ToDoResponseDto> findAll() {
        return toDoRepository.findAll().stream()
                .map(todo -> new ToDoResponseDto(todo.getId(), todo.getContent(), todo.isDone()))
                .toList();
    }

    @Transactional
    public void updateToDo(Long targetId, ToDoRequestDto dto) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 할 일이 존재하지 않습니다."));
        toDo.updateContent(dto.getContent());
    }

    @Transactional
    public void deleteToDo(Long targetId) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 할 일이 존재하지 않습니다."));
        toDoRepository.delete(toDo);
    }

}
