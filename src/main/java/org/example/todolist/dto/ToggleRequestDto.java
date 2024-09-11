package org.example.todolist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToggleRequestDto {

    @NotNull
    private boolean done;

}
