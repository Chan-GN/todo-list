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
        Member member = memberRepository.getReferenceById(memberId);
        Team team = Team.of(dto.getTeamName());

        TeamMember teamMember = TeamMember.of(member, team);

        teamMemberRepository.save(teamMember);

        return teamMember.getId();
    }

}
