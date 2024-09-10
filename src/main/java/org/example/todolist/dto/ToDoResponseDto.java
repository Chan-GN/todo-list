package org.example.todolist.dto;

import lombok.Data;

@Data
public class ToDoResponseDto {

    private Long id;

    private String content;

    private boolean isDone;

    public ToDoResponseDto(Long id, String content, boolean isDone) {
        this.id = id;
        this.content = content;
        this.isDone = isDone;
    }

}
