package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.Member;
import org.example.todolist.dto.auth.SignInRequestDto;
import org.example.todolist.dto.auth.SignUpRequestDto;
import org.example.todolist.exception.DuplicateLoginIdException;
import org.example.todolist.repository.MemberRepository;
import org.example.todolist.security.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * 회원가입
     */
    @Transactional
    public Long saveMember(SignUpRequestDto dto) {
        checkDuplicateLoginId(dto.getLoginId());
        Member member = Member.of(dto.getLoginId(), passwordEncoder.encode(dto.getPassword()), dto.getName());
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    /**
     * 로그인
     */
    public CustomUserDetails authenticate(SignInRequestDto dto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getPassword())
        );
        return (CustomUserDetails) authenticate.getPrincipal();
    }

    /**
     * 아이디 중복 체크
     */
    private void checkDuplicateLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(m -> {
                    throw new DuplicateLoginIdException("이미 존재하는 아이디입니다.");
                });
    }

}
