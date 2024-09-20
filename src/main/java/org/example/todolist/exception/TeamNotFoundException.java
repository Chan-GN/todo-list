package org.example.todolist.exception;

/**
 * 팀을 찾을 수 없는 예외를 표현하는 클래스
 */
public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(String message) {
        super(message);
    }

}
