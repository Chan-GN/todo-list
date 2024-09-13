package org.example.todolist.repository;

import org.example.todolist.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 정보에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

}
