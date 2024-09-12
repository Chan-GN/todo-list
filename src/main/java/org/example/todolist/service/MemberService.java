package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.Member;
import org.example.todolist.dto.member.MemberResponseDto;
import org.example.todolist.exception.MemberNotFoundException;
import org.example.todolist.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 사용자 정보 조회
     */
    public MemberResponseDto getMemberByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException("사용자 정보를 찾을 수 없습니다."));
        return new MemberResponseDto(member.getId(), member.getLoginId(), member.getName());
    }

}
