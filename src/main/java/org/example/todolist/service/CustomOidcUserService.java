package org.example.todolist.service;

import org.example.todolist.config.CustomUserDetails;
import org.example.todolist.domain.Member;
import org.example.todolist.repository.MemberRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final MemberRepository memberRepository;

    public CustomOidcUserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        Member member = memberRepository.findByLoginId(email)
                .orElseGet(() -> createMember(email, name));

        return new CustomUserDetails(member, oidcUser);
    }

    private Member createMember(String email, String name) {
        Member newMember = Member.of(email, "OAUTH2_USER", name);
        return memberRepository.save(newMember);
    }

}