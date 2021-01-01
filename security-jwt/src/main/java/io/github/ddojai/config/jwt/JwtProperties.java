package io.github.ddojai.config.jwt;

public interface JwtProperties {
  String SECRET = "cos";
  int EXPIRATION_TIME = 60000 * 10; // 10
  String TOKEN_PREFIX = "Bearer ";
  String HEADER_STRING = "Authorization";
}
