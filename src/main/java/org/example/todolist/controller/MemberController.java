package org.example.todolist.controller;

import lombok.RequiredArgsConstructor;
import org.example.todolist.dto.member.MemberResponseDto;
import org.example.todolist.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponseDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String loginId = userDetails.getUsername();
        MemberResponseDto memberDto = memberService.getMemberByLoginId(loginId);
        return ResponseEntity.ok(memberDto);
    }

}