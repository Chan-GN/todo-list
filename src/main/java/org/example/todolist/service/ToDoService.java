package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.ToDo;
import org.example.todolist.dto.ToDoRequestDto;
import org.example.todolist.dto.ToDoResponseDto;
import org.example.todolist.dto.ToggleRequestDto;
import org.example.todolist.exception.ToDoNotFoundException;
import org.example.todolist.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ToDoService {

    private final ToDoRepository toDoRepository;

    /**
     * 할 일 추가
     */
    @Transactional
    public void saveToDo(ToDoRequestDto dto) {
        ToDo toDo = ToDo.of(dto.getContent());
        toDoRepository.save(toDo);
    }

    /**
     * 할 일 목록 조회
     */
    public List<ToDoResponseDto> findAll() {
        return toDoRepository.findAllSorted().stream()
                .map(todo -> new ToDoResponseDto(todo.getId(), todo.getContent(), todo.isDone()))
                .toList();
    }

    /**
     * 할 일 수정
     */
    @Transactional
    public void updateToDo(Long targetId, ToDoRequestDto dto) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new ToDoNotFoundException("수정하려는 할 일이 존재하지 않습니다."));
        toDo.updateContent(dto.getContent());
    }

    /**
     * 할 일 삭제
     */
    @Transactional
    public void deleteToDo(Long targetId) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new ToDoNotFoundException("삭제하려는 할 일이 존재하지 않습니다."));
        toDoRepository.delete(toDo);
    }

    /**
     * 할 일 완료 상태 변경
     */
    @Transactional
    public void toggleDone(Long targetId, ToggleRequestDto dto) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new ToDoNotFoundException("할 일이 존재하지 않습니다."));
        toDo.updateDone(dto.isDone());
    }

}
