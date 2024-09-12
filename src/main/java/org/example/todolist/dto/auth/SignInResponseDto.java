package org.example.todolist.dto.auth;

import lombok.Data;

@Data
public class SignInResponseDto {

    private String token;

    private long expiresIn;

    public SignInResponseDto(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

}