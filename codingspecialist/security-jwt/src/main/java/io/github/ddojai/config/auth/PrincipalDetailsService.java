package io.github.ddojai.config.auth;

import io.github.ddojai.model.User;
import io.github.ddojai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("PrincipalDetailsService의 loadUserByUsername()");
    User user = userRepository.findByUsername(username);

    return new PrincipalDetails(user);
  }
}
