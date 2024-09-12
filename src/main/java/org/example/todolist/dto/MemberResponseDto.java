package org.example.todolist.dto;

import lombok.Data;

@Data
public class MemberResponseDto {

    private Long id;
    private String loginId;
    private String name;

    public MemberResponseDto(Long id, String loginId, String name) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
    }

}
