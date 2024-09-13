package org.example.todolist.exception;

/**
 * 중복된 로그인 ID 예외를 표현하는 클래스
 */
public class DuplicateLoginIdException extends RuntimeException {

    public DuplicateLoginIdException(String message) {
        super(message);
    }

}
