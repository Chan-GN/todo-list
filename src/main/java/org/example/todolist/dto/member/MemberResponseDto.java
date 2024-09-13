package org.example.todolist.dto.member;

import lombok.Data;

/**
 * 회원 정보 응답을 담는 DTO 클래스
 */
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
