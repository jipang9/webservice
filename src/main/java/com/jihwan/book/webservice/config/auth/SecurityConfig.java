package com.jihwan.book.webservice.config.auth;

import com.jihwan.book.webservice.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;


@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화 시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()// h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
                .and()
                .authorizeRequests()// URL 별 권한 관리를 설정하는 옵션의 시작점으로, authorizeRequests가 선언되어야만
//                                      antMatchers 옵션을 사용할 수 있다.
                .antMatchers("/","/css/**","/images/**",
                        "/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권한 관리 대상을 지정하는 옵션으로,
//                                                                                     URL, HTTP 메소드별 관리가 가능하다.
//                                                                                     "/" 등 지정된 URL은 permitAll() 옵션을 통해 전체 열람 권한을 가지며
//                                                                                     Post 메소드 이면서 "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하다.

                .anyRequest().authenticated() // 설정된 값들 이외의 나머지 URL을 나타낸다. 여기서는 authenticate()를 추가해 나머지 URL들은 모드 인증된 사용자들에게만 허용
//                                               인증된 사용자 즉, 로그인한 사용자들을 이야기 한다.
                .and()
                .logout().logoutSuccessUrl("/") // 로그아웃 기능에 대한 여러 설정의 진입점으로, 로그아웃 성공시 / 주소로 이동한다.
                .and()
                .oauth2Login() // OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.
                .userInfoEndpoint()// 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
                .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행항 UserSevice 인터페이스의 구현체를 등록하며
//                                                                 리소스 서버(소셜서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
    }
}
