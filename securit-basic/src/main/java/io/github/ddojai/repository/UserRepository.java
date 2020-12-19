package io.github.ddojai.repository;

import io.github.ddojai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String username);
}
