package org.example.todolist.dto.auth;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String loginId;

    private String password;

}
