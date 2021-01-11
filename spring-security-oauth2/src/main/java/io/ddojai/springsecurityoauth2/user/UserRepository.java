package io.ddojai.springsecurityoauth2.user;


import io.ddojai.springsecurityoauth2.social.userconnection.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findBySocial(UserConnection userConnection);

}
