package org.example.todolist.repository;

import org.example.todolist.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 팀과 회원 간의 관계에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findByTeamIdAndMemberId(Long teamId, Long memberId);

    boolean existsByTeamIdAndMemberId(Long teamId, Long memberId);

}
