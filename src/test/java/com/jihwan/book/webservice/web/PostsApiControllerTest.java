package com.jihwan.book.webservice.web;

import com.jihwan.book.webservice.domain.posts.Posts;
import com.jihwan.book.webservice.domain.posts.PostsRepository;
import com.jihwan.book.webservice.web.dto.PostsSaveRequestDto;
import com.jihwan.book.webservice.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception{
        //Given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:"+ port +"/api/v1/posts";

        //When
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception{
        //Given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String exceptedTitle = " + port + "/api/v1/posts/"+ updateId;

        HttpEntity<PostsUpdateRequestDto>requestEntity = new HttpEntity<>(requestDto);

        //When
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.POST,requesttitle";
                String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(exceptedTitle)
                .content(expectedContent)
                .build();
        String url = "http://localhost:"Entity,Long.class);

        //@Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(exceptedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
