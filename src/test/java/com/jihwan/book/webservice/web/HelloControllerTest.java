package com.jihwan.book.webservice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


// @SpringBootTest
// 책의 본 편에서는 @RunWith 어노테이션을 사용했는데, 더이상 사용하지 않는다.(JUnit4 -> 5)
// 그래서 Junit5 부터는 @RunWith가 아닌 Extension이라는 방법을 사용한다 (@ExtendWith(SpringExtension.class))
// 하지만 위 코드 역시 생략이 가능한데 그 이유는 @SpringBootTest가 이미 그 코드를 가지고 있기에  다시 선언하지 않아도 된다.

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    //스프링이 관리하는 빈(Bean)을 주입 받는다.
    private MockMvc mvc;
    // 웹 API를 테스트할 때 사용하며, 스프링 MVC 테스트의 시작점이다.
    // 이 클래스를 통해 HTTP GET, POST 등에 대한 API를 테스트 할 수 있다.
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello"))
                // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 하며, 체이닝이 지원되 아래와 같이 검증 기능을 이어 선언할 수 있다.
                .andExpect(status().isOk())
                // 여기서는 HTTP Header의 Status를 검증하는데, 여기서는 OK 즉, 200인지 아닌지를 검증한다.
                .andExpect(content().string(hello));
                // 응답 본문의 내용을 검증하는데, Controller에서 "hello"를 리턴하기에 이 값이 맞는지 검증한다.
    }
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                        get("/hello/dto")
                                .param("name", name)
                                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}
