package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.Member;
import org.example.todolist.domain.ToDo;
import org.example.todolist.dto.todo.ToDoRequestDto;
import org.example.todolist.dto.todo.ToDoResponseDto;
import org.example.todolist.dto.todo.ToggleRequestDto;
import org.example.todolist.exception.MemberNotFoundException;
import org.example.todolist.exception.ToDoNotFoundException;
import org.example.todolist.exception.UnauthorizedException;
import org.example.todolist.repository.MemberRepository;
import org.example.todolist.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ToDoService {

    private final ToDoRepository toDoRepository;

    private final MemberRepository memberRepository;

    /**
     * 할 일 추가
     */
    @Transactional
    public Long saveToDo(ToDoRequestDto dto, String currentMemberLoginId) {
        Member member = memberRepository.findByLoginId(currentMemberLoginId)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보가 존재하지 않습니다."));
        ToDo toDo = ToDo.of(dto.getContent(), member);
        return toDoRepository.save(toDo).getId();
    }

    /**
     * 할 일 목록 조회
     */
    public List<ToDoResponseDto> findAll(Long currentMemberId) {
        return toDoRepository.findAllSorted(currentMemberId).stream()
                .map(todo -> new ToDoResponseDto(todo.getId(), todo.getContent(), todo.isDone()))
                .toList();
    }

    /**
     * 할 일 수정
     */
    @Transactional
    public void updateToDo(Long targetId, ToDoRequestDto dto, Long currentMemberId) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new ToDoNotFoundException("수정하려는 할 일이 존재하지 않습니다."));
        validateToDoOwnership(toDo.getMember().getId(), currentMemberId);
        toDo.updateContent(dto.getContent());
    }

    /**
     * 할 일 삭제
     */
    @Transactional
    public void deleteToDo(Long targetId, Long currentMemberId) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new ToDoNotFoundException("삭제하려는 할 일이 존재하지 않습니다."));
        validateToDoOwnership(toDo.getMember().getId(), currentMemberId);
        toDoRepository.delete(toDo);
    }

    /**
     * 할 일 완료 상태 변경
     */
    @Transactional
    public void toggleDone(Long targetId, ToggleRequestDto dto, Long currentMemberId) {
        ToDo toDo = toDoRepository.findById(targetId)
                .orElseThrow(() -> new ToDoNotFoundException("할 일이 존재하지 않습니다."));
        validateToDoOwnership(toDo.getMember().getId(), currentMemberId);
        toDo.updateDone(dto.isDone());
    }

    private void validateToDoOwnership(Long todoOwnerId, Long currentMemberId) {
        if (!todoOwnerId.equals(currentMemberId)) {
            throw new UnauthorizedException("이 할 일을 수정할 권한이 없습니다.");
        }
    }

}
