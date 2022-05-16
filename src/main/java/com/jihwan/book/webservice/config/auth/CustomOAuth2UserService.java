package com.jihwan.book.webservice.config.auth;


import com.jihwan.book.webservice.config.auth.dto.OAuthAttributes;
import com.jihwan.book.webservice.config.auth.dto.SessionUser;
import com.jihwan.book.webservice.domain.posts.UserRepository;
import com.jihwan.book.webservice.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
        throws OAuth2AuthenticationException{
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2USer = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드로, 현재는 구글만 사용하는 불필요한 값이지만,
//                                                               이후 네이버 로그인 연동 시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용.
        String userNameAttributeName = userRequest.
                getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().
                getUserNameAttributeName();// OAuth2 로그인 진행 시 키가 되는 필드 값을 이야기 하며, Primary Key와 같은 의미다.
//                                            구글의 경우 기본적으로 코드를 지원하지만, 네이버, 카카오 같은 경우엔 기본을 지원하지 않으며 구글의 기본 코드는 "sub"
//                                             이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.

        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2USer.getAttributes()); // OAuth2UserService를 통해 가져온 OAuth2User의 Attribute를 담은 클래스로,
//                                                                                        이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용한다.

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));// 세션에 사용자 정보를 저장하기 위한 Dto 클래스로, 왜 User클래스를 쓰지 않고 새로 만들어서 사용?

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributekey());
    }
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
