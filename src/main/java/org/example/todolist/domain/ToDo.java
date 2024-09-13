package org.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * To-Do 항목을 표현하는 엔티티 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private ToDo(String content, boolean done, Member member) {
        this.content = content;
        this.done = done;
        this.member = member;
    }

    public static ToDo of(String content, Member member) {
        return new ToDo(content, false, member);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateDone(boolean done) {
        this.done = done;
    }

}