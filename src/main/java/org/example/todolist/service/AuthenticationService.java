package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.config.CustomUserDetails;
import org.example.todolist.domain.Member;
import org.example.todolist.dto.SignInRequestDto;
import org.example.todolist.dto.SignUpRequestDto;
import org.example.todolist.exception.DuplicateLoginIdException;
import org.example.todolist.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public Long saveMember(SignUpRequestDto dto) {
        checkDuplicateLoginId(dto.getLoginId());
        Member member = Member.of(dto.getLoginId(), passwordEncoder.encode(dto.getPassword()), dto.getName());
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public CustomUserDetails authenticate(SignInRequestDto dto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getPassword())
        );
        return (CustomUserDetails) authenticate.getPrincipal();
    }

    private void checkDuplicateLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(m -> {
                    throw new DuplicateLoginIdException("이미 존재하는 아이디입니다.");
                });
    }

}
