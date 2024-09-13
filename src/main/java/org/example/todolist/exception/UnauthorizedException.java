package org.example.todolist.exception;

/**
 * 인증되지 않은 접근 예외를 표현하는 클래스
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

}
