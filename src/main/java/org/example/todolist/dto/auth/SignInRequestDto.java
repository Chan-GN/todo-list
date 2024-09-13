package org.example.todolist.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 로그인 요청 정보를 담는 DTO 클래스
 */
@Data
public class SignInRequestDto {

    @NotBlank(message = "로그인 ID는 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "로그인 ID는 4자 이상 20자 이하여야 합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 50, message = "비밀번호는 8자 이상 50자 이하여야 합니다.")
    private String password;

}
