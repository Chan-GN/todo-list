package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.*;
import org.example.todolist.dto.InvitationRequestDto;
import org.example.todolist.exception.InvitationNotFoundException;
import org.example.todolist.exception.MemberNotFoundException;
import org.example.todolist.exception.TeamNotFoundException;
import org.example.todolist.repository.InvitationRepository;
import org.example.todolist.repository.MemberRepository;
import org.example.todolist.repository.TeamMemberRepository;
import org.example.todolist.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 초대 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationService {

    private final InvitationRepository invitationRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final MemberRepository memberRepository;

    private final TeamRepository teamRepository;

    private final InvitationValidator validator;

    /**
     * 초대 생성
     */
    @Transactional
    public Long createInvitation(InvitationRequestDto dto, Long inviterId) {
        validator.validateInviter(dto.getTeamId(), inviterId);
        Member invitee = findInvitee(dto.getInviteeLoginId());
        Member inviter = memberRepository.getReferenceById(inviterId);
        Team team = findTeam(dto.getTeamId());

        validator.validateInviteeNotInTeam(dto.getTeamId(), invitee.getId());
        validator.validateNoPendingInvitation(dto.getTeamId(), invitee.getId());

        Invitation invitation = Invitation.of(team, inviter, invitee);
        Invitation savedInvitation = invitationRepository.save(invitation);

        // TODO: 초대된 사람에게 알림

        return savedInvitation.getId();
    }

    /**
     * 초대 수락
     */
    @Transactional
    public void acceptInvitation(Long invitationId, Long inviteeId) {
        Invitation invitation = findInvitation(invitationId);
        validator.validateInvitationOwnership(invitation, inviteeId);
        validator.validateInvitationStatus(invitation, InvitationStatus.PENDING);

        invitation.accept();

        TeamMember teamMember = TeamMember.of(invitation.getInvitee(), invitation.getTeam());
        teamMemberRepository.save(teamMember);
    }

    /**
     * 초대 거부
     */
    @Transactional
    public void rejectInvitation(Long invitationId, Long inviteeId) {
        Invitation invitation = findInvitation(invitationId);
        validator.validateInvitationOwnership(invitation, inviteeId);
        validator.validateInvitationStatus(invitation, InvitationStatus.PENDING);

        invitation.reject();
    }

    private Member findInvitee(String inviteeLoginId) {
        return memberRepository.findByLoginId(inviteeLoginId)
                .orElseThrow(() -> new MemberNotFoundException("초대하려는 회원 정보가 존재하지 않습니다."));
    }

    private Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("팀 정보가 존재하지 않습니다."));
    }

    private Invitation findInvitation(Long invitationId) {
        return invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationNotFoundException("초대 정보가 존재하지 않습니다."));
    }

}