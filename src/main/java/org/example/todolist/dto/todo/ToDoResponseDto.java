package org.example.todolist.dto.todo;

import lombok.Data;

/**
 * To-Do 항목 응답 정보를 담는 DTO 클래스
 */
@Data
public class ToDoResponseDto {

    private Long id;

    private String content;

    private boolean done;

    public ToDoResponseDto(Long id, String content, boolean done) {
        this.id = id;
        this.content = content;
        this.done = done;
    }

}
