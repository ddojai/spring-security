package com.tistory.ddojai.springsecurity.controller;

import com.tistory.ddojai.springsecurity.domain.User;
import com.tistory.ddojai.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserRepository userRepository;

  @GetMapping(value = "/me")
  public User me() {
    // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return userRepository.findByEmail(email)
      .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 " +
        "E-MAIL 입니다."));
  }
}
