package io.github.ddojai.config.oauth;

import io.github.ddojai.config.auth.PrincipalDetails;
import io.github.ddojai.model.User;
import io.github.ddojai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private UserRepository userRepository;

  // 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
  // 함수 종료시 @AuthenticationPrincipal 어노테이션 생성
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // registrationId로 어떤 oauth로 로그인 하는지 확인
    System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
    System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

    OAuth2User oAuth2User = super.loadUser(userRequest);
    // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code를 리턴(oauth-client 라이브러리리) -> AccessToken요청
    // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아준다.
    System.out.println("getAttributes: " + oAuth2User.getAttributes());

    String provider = userRequest.getClientRegistration().getClientId(); // google
    String providerId = oAuth2User.getAttribute("sub");
    String username = provider + "_" + providerId; // google_101812839028309
    String password = bCryptPasswordEncoder.encode("겟인데어");
    String email = oAuth2User.getAttribute("email");
    String role = "ROLE_USER";

    User userEntity = userRepository.findByUsername(username);
    if (userEntity == null) {
      userEntity = User.builder()
        .username(username)
        .password(password)
        .email(email)
        .role(role)
        .provider(provider)
        .providerId(providerId)
        .build();
      userRepository.save(userEntity);
    }

    return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
  }
}
