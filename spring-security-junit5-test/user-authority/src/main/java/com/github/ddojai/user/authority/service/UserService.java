package com.github.ddojai.user.authority.service;

import com.github.ddojai.user.authority.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.github.ddojai.user.authority.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  public User save(User user) {
    if (StringUtils.isEmpty(user.getId())) {
      user.setCreated(LocalDateTime.now());
    }
    user.setUpdated(LocalDateTime.now());
//    user.setEnabled(true);

    return userRepository.save(user);
  }

  public Optional<User> findUser(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException(username + " 사용자를 찾을 수 없습니다."));
  }
}
