package org.example.todolist.controller;

import org.example.todolist.domain.Member;
import org.example.todolist.dto.member.MemberResponseDto;
import org.example.todolist.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 정보 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @GetMapping
    public ResponseEntity<MemberResponseDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(
                new MemberResponseDto(member.getId(), member.getLoginId(), member.getName())
        );
    }

}