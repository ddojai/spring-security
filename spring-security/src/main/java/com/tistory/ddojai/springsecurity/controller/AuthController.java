package com.tistory.ddojai.springsecurity.controller;

import com.tistory.ddojai.springsecurity.domain.User;
import com.tistory.ddojai.springsecurity.payload.AuthResponse;
import com.tistory.ddojai.springsecurity.repository.UserRepository;
import com.tistory.ddojai.springsecurity.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody Map<String, String> user) {
    User newUser = User.builder()
      .email(user.get("email"))
      .password(passwordEncoder.encode(user.get("password")))
      .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
      .build();

    userRepository.save(newUser);

    String token = jwtTokenProvider.createToken(newUser.getUsername(), newUser.getRoles());

    return ResponseEntity.ok(new AuthResponse(token));
  }

  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
    User member = userRepository.findByEmail(user.get("email"))
      .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
    if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
      throw new IllegalArgumentException("잘못된 비밀번호입니다.");
    }
    String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

    return ResponseEntity.ok(new AuthResponse(token));
  }
}
