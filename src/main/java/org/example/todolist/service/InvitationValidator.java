package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.Invitation;
import org.example.todolist.domain.InvitationStatus;
import org.example.todolist.exception.UnauthorizedException;
import org.example.todolist.repository.InvitationRepository;
import org.example.todolist.repository.TeamMemberRepository;
import org.springframework.stereotype.Component;

/**
 * 초대 관련 검증을 담당하는 클래스
 */
@Component
@RequiredArgsConstructor
public class InvitationValidator {

    private final TeamMemberRepository teamMemberRepository;

    private final InvitationRepository invitationRepository;

    /**
     * 초대한 사람이 해당 팀에 소속되어 있는지 검증
     */
    public void validateInviter(Long teamId, Long inviterId) {
        boolean isValidMember = teamMemberRepository.existsByTeamIdAndMemberId(teamId, inviterId);
        if (!isValidMember) {
            throw new UnauthorizedException("초대 권한이 없습니다.");
        }
    }

    /**
     * 초대된 사람이 이미 팀에 소속되어 있는지 검증
     */
    public void validateInviteeNotInTeam(Long teamId, Long inviteeId) {
        boolean isAlreadyMember = teamMemberRepository.existsByTeamIdAndMemberId(teamId, inviteeId);
        if (isAlreadyMember) {
            throw new IllegalStateException("이미 팀에 소속된 회원입니다.");
        }
    }

    /**
     * 이미 대기 중인 초대가 있는지 검증
     */
    public void validateNoPendingInvitation(Long teamId, Long inviteeId) {
        boolean hasPendingInvitation = invitationRepository.existsByTeamIdAndInviteeIdAndStatus(
                teamId, inviteeId, InvitationStatus.PENDING);
        if (hasPendingInvitation) {
            throw new IllegalStateException("이미 대기 중인 초대가 있습니다.");
        }
    }

    /**
     * 초대된 사람이 초대 정보에 저장된 초대된 사람과 일치하는지 검증
     */
    public void validateInvitationOwnership(Invitation invitation, Long inviteeId) {
        if (!invitation.getInvitee().getId().equals(inviteeId)) {
            throw new UnauthorizedException("해당 초대에 대한 권한이 없습니다.");
        }
    }

    /**
     * 초대 상태 2개를 입력받아 일치하는지 검증
     */
    public void validateInvitationStatus(Invitation invitation, InvitationStatus expectedStatus) {
        if (invitation.getStatus() != expectedStatus) {
            throw new IllegalStateException("유효하지 않은 초대 상태입니다.");
        }
    }

}