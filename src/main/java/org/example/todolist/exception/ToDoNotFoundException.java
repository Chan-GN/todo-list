package org.example.todolist.exception;

/**
 * To-Do 항목을 찾을 수 없는 예외를 표현하는 클래스
 */
public class ToDoNotFoundException extends RuntimeException {

    public ToDoNotFoundException(String message) {
        super(message);
    }
    
}
