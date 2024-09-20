package org.example.todolist.dto;

import lombok.Data;

/**
 * 초대 생성 요청 정보를 담는 DTO 클래스
 */
@Data
public class InvitationRequestDto {

    private Long teamId;

    private String inviteeLoginId;

}
