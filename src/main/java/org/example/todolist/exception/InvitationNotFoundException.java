package org.example.todolist.exception;

/**
 * 초대 정보를 찾을 수 없는 예외를 표현하는 클래스
 */
public class InvitationNotFoundException extends RuntimeException {

    public InvitationNotFoundException(String message) {
        super(message);
    }

}
