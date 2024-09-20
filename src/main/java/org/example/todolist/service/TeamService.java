package org.example.todolist.service;

import lombok.RequiredArgsConstructor;
import org.example.todolist.domain.Member;
import org.example.todolist.domain.Team;
import org.example.todolist.domain.TeamMember;
import org.example.todolist.dto.TeamRequestDto;
import org.example.todolist.repository.MemberRepository;
import org.example.todolist.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 팀 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamMemberRepository teamMemberRepository;

    private final MemberRepository memberRepository;

    /**
     * 팀 생성
     */
    @Transactional
    public Long createTeam(Long memberId, TeamRequestDto dto) {
        // Member 엔티티의 존재가 확실하므로 getReferenceById 메서드 사용
        Member member = memberRepository.getReferenceById(memberId);
        Team team = Team.of(dto.getTeamName());

        TeamMember teamMember = TeamMember.of(member, team);

        teamMemberRepository.save(teamMember);

        return teamMember.getId();
    }

}
