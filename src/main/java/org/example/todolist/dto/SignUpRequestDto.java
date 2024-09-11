package org.example.todolist.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String loginId;

    private String password;

    private String name;

}
