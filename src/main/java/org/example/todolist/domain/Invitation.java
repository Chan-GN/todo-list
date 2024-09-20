package org.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 팀 초대 정보를 표현하는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member inviter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member invitee;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    private LocalDateTime createdAt;

    private Invitation(Team team, Member inviter, Member invitee) {
        this.team = team;
        this.inviter = inviter;
        this.invitee = invitee;
        this.status = InvitationStatus.PENDING;
        this.createdAt = LocalDateTime.now(); // TODO Auditing 고려
    }

    public static Invitation of(Team team, Member inviter, Member invitee) {
        return new Invitation(team, inviter, invitee);
    }

    public void accept() {
        this.status = InvitationStatus.ACCEPTED;
    }

    public void reject() {
        this.status = InvitationStatus.REJECTED;
    }

}
