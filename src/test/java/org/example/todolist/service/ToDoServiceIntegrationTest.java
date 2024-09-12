package org.example.todolist.service;

import org.example.todolist.domain.Member;
import org.example.todolist.domain.ToDo;
import org.example.todolist.dto.to_do.ToDoRequestDto;
import org.example.todolist.dto.to_do.ToDoResponseDto;
import org.example.todolist.exception.MemberNotFoundException;
import org.example.todolist.repository.MemberRepository;
import org.example.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ToDoServiceIntegrationTest {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = Member.of("testUser", "password", "Test User");
        memberRepository.save(testMember);
    }

    @Test
    void 할_일_추가() {
        ToDo savedToDo = createNewToDo("Test ToDo Item");

        assertNotNull(savedToDo);
        assertEquals("Test ToDo Item", savedToDo.getContent());
        assertEquals(testMember.getLoginId(), savedToDo.getMember().getLoginId());
    }

    @Test
    void 할_일_추가_없는_사용자_예외() {
        ToDoRequestDto dto = new ToDoRequestDto();
        dto.setContent("Test ToDo Item");

        assertThrows(MemberNotFoundException.class, () -> {
            toDoService.saveToDo(dto, "nonExistentUser");
        });
    }

    @Test
    void 할_일_목록_조회() {
        // Given
        ToDo newToDo1 = createNewToDo("First Todo");
        ToDo newToDo2 = createNewToDo("Second Todo");

        // When
        List<ToDoResponseDto> dtoList = toDoService.findAll(testMember.getId());

        // Then
        assertEquals(2, dtoList.size());
        assertEquals("Second Todo", dtoList.get(0).getContent());
        assertEquals("First Todo", dtoList.get(1).getContent());
        assertFalse(dtoList.get(0).isDone());
        assertFalse(dtoList.get(1).isDone());
    }

    private ToDo createNewToDo(String content) {
        ToDoRequestDto dto = new ToDoRequestDto();
        dto.setContent(content);

        Long savedToDoId = toDoService.saveToDo(dto, testMember.getLoginId());

        return toDoRepository.findById(savedToDoId)
                .orElseThrow(() -> new AssertionError("ToDo not found"));
    }

}