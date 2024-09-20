# ToDo List API Server

## 프로젝트 소개

이 프로젝트는 Spring Boot를 사용하여 구현된 ToDo List API Server입니다.
사용자 인증, ToDo 항목 관리 등의 기능을 제공하며, JWT를 이용한 보안 시스템과 OAuth2를 통한 소셜 로그인을 지원합니다.

## 개발 환경

- 개발 언어: **`Java`**
- 프레임워크: **`Spring Boot 3`**, **`Spring Security`**
- 데이터베이스: `MySQL`
- 인증: `JWT`, `OAuth2`
- API 문서화: `Swagger`

## 주요 기능

1. **사용자 인증 및 권한 관리**
    - JWT를 이용한 토큰 기반 인증
    - OAuth2를 통한 구글 로그인
2. **ToDo 항목 관리**
    - ToDo 항목 생성, 조회, 수정, 삭제
    - ToDo 항목 완료 상태 토글
3. **사용자 정보 관리**
4. **예외 처리 및 에러 응답**

## 프로젝트 구조

```
org.example.todolist
├── config
│   ├── jwt
│   │   ├── JwtProvider.java
│   │   └── JwtAuthenticationFilter.java
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
├── controller
│   ├── AuthenticationController.java
│   ├── MemberController.java
│   └── ToDoController.java
├── domain
│   ├── Member.java
│   └── ToDo.java
├── dto
│   ├── auth
│   │   ├── SignInRequestDto.java
│   │   ├── SignInResponseDto.java
│   │   └── SignUpRequestDto.java
│   ├── member
│   │   └── MemberResponseDto.java
│   └── todo
│       ├── ToDoRequestDto.java
│       ├── ToDoResponseDto.java
│       └── ToggleRequestDto.java
├── exception
│   ├── DuplicateLoginIdException.java
│   ├── GlobalExceptionHandler.java
│   ├── MemberNotFoundException.java
│   ├── ToDoNotFoundException.java
│   └── UnauthorizedException.java
├── repository
│   ├── MemberRepository.java
│   └── ToDoRepository.java
├── security
│   ├── CustomOidcUserService.java
│   ├── CustomUserDetails.java
│   ├── CustomUserDetailsService.java
│   └── OAuth2LoginSuccessHandler.java
└── service
    ├── AuthenticationService.java
    └── ToDoService.java
```

## 주요 구현 사항

1. **JWT 인증 시스템**
    - `JwtProvider`를 통한 토큰 생성 및 검증
    - `JwtAuthenticationFilter`를 이용한 요청 인증 처리
2. **OAuth2 소셜 로그인**
    - `CustomOidcUserService`를 통한 OAuth2 사용자 정보 처리
    - `OAuth2LoginSuccessHandler`를 이용한 로그인 성공 후 처리
3. **예외 처리**
    - `GlobalExceptionHandler`를 통한 통합 예외 처리
4. **Swagger를 이용한 API 문서화**
    - `SwaggerConfig`를 통한 Swagger 설정
5. **Spring Security 설정**
    - `SecurityConfig`를 통한 보안 설정

## API 명세서

| 엔드포인트                          | 메서드    | 설명            | 요청      | 응답                   | 인증 필요 |
|--------------------------------|--------|---------------|---------|----------------------|-------|
| `/api/auth/join`               | POST   | 회원가입          | 사용자 정보  | 201 Created, 사용자 ID  | 아니오   |
| `/api/auth/login`              | POST   | 로그인           | 로그인 정보  | 200 OK, JWT 토큰       | 아니오   |
| `/api/member`                  | GET    | 현재 사용자 정보 조회  | -       | 200 OK, 사용자 정보       | 예     |
| `/api/to-do`                   | POST   | Todo 항목 추가    | Todo 내용 | 201 Created, Todo ID | 예     |
| `/api/to-do`                   | GET    | Todo 목록 조회    | -       | 200 OK, Todo 목록      | 예     |
| `/api/to-do/{targetId}`        | PUT    | Todo 항목 수정    | 수정 내용   | 200 OK               | 예     |
| `/api/to-do/{targetId}/toggle` | PUT    | Todo 완료 상태 변경 | 완료 상태   | 200 OK               | 예     |
| `/api/to-do/{targetId}`        | DELETE | Todo 항목 삭제    | -       | 200 OK               | 예     |

### 기본 정보

- 인증 방식: JWT (JSON Web Token)
- 보안이 필요한 엔드포인트는 Authorization 헤더에 Bearer 토큰 필요