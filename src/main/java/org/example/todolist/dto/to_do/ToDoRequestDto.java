package org.example.todolist.dto.to_do;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToDoRequestDto {

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

}
