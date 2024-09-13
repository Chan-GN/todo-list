package org.example.todolist.exception;

/**
 * 회원을 찾을 수 없는 예외를 표현하는 클래스
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
