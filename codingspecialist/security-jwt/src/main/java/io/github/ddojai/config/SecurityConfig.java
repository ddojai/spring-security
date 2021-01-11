package io.github.ddojai.config;

import io.github.ddojai.config.jwt.JwtAuthenticationFilter;
import io.github.ddojai.config.jwt.JwtAuthorizationFilter;
import io.github.ddojai.filter.MyFilter3;
import io.github.ddojai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CorsFilter corsFilter;
  private final UserRepository userRepository;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilter(corsFilter)  // @CrossOrigin은 인증이 필요 하지 않을 때 만, 인증이 필요할 때 는 시큐리티 필터에 등록
      .formLogin().disable()
      .httpBasic().disable()
      .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager
      .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository)) // AuthenticationManager
      .authorizeRequests()
      .antMatchers("/api/v1/user/**")
      .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
      .antMatchers("/api/v1/manager/**")
      .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
      .antMatchers("/api/v1/admin/**")
      .access("hasRole('ROLE_ADMIN')")
      .anyRequest()
      .permitAll();
  }
}
