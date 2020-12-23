package io.github.ddojai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록 됨
// secured 어노테이션 활성화, preAuthorize & postAuthorize 어노테이션 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public BCryptPasswordEncoder encodePwd() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
      .antMatchers("/user/**").authenticated()  // 인증만 되면 들어갈 수 있는 주소
      .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
      .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
      .anyRequest().permitAll()
      .and()
      .formLogin()
      .loginPage("/loginForm")
      .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 대신 로그인을 진행
      .defaultSuccessUrl("/")
      .and()
      .oauth2Login()
      .loginPage("/loginForm");
  }
}
