package org.example.todolist.dto.todo;

import lombok.Data;

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
