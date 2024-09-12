package org.example.todolist.controller;

import lombok.RequiredArgsConstructor;
import org.example.todolist.config.CustomUserDetails;
import org.example.todolist.config.JwtProvider;
import org.example.todolist.dto.auth.SignInRequestDto;
import org.example.todolist.dto.auth.SignInResponseDto;
import org.example.todolist.dto.auth.SignUpRequestDto;
import org.example.todolist.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtProvider jwtProvider;

    @PostMapping("/join")
    public ResponseEntity<Long> register(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.saveMember(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponseDto> authenticate(@RequestBody SignInRequestDto dto) {
        CustomUserDetails userDetails = authenticationService.authenticate(dto);

        String jwtToken = jwtProvider.generateToken(userDetails);

        SignInResponseDto signInResponseDto = new SignInResponseDto(jwtToken, jwtProvider.getExpirationTime());

        return ResponseEntity.ok(signInResponseDto);
    }

}
