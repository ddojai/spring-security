package com.github.ddojai.user.authority.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authority implements GrantedAuthority {

  public static final String ROLE_USER = "ROLE_USER";
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  public static final Authority USER = new Authority(ROLE_USER);
  public static final Authority ADMIN = new Authority(ROLE_ADMIN);

  private String authority;

  @Override
  public String getAuthority() {
    return null;
  }
}
