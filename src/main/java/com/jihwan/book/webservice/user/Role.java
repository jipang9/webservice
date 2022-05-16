package com.jihwan.book.webservice.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 시큐리티에서 권한 코드에 항상 ROLE_이 앞에 있어야 함.
    GUEST("ROLE_GUTST", "손님"),
    USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;
}
