package io.github.ddojai.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // registrationId로 어떤 oauth로 로그인 하는지 확
    System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
    System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
    // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code를 리턴(oauth-client 라이브러리리) -> AccessToken요청
    // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아준다.
    System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

    OAuth2User oAuth2User = super.loadUser(userRequest);
    // 회원가입 진행

    return super.loadUser(userRequest);
  }
}
