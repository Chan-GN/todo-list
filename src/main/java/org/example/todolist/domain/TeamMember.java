package org.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팀과 회원 간의 다대다 관계를 표현하는 엔티티 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
    }

    public static TeamMember of(Member member, Team team) {
        return new TeamMember(member, team);
    }

}
