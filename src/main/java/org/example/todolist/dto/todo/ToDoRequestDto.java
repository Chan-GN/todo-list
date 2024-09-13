package org.example.todolist.dto.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * To-Do 항목 생성/수정 요청 정보를 담는 DTO 클래스
 */
@Data
public class ToDoRequestDto {

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

}
