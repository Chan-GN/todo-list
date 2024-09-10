package org.example.todolist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean done;

    private ToDo(String content, boolean done) {
        this.content = content;
        this.done = done;
    }

    public static ToDo of(String content) {
        return new ToDo(content, false);
    }

    public void updateContent(String content) {
        this.content = content;
    }

}