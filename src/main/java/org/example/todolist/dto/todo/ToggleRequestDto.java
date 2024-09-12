package org.example.todolist.dto.todo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToggleRequestDto {

    @NotNull
    private boolean done;

}
