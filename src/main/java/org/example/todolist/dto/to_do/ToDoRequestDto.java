package org.example.todolist.dto.to_do;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToDoRequestDto {

    @NotBlank
    private String content;

}
