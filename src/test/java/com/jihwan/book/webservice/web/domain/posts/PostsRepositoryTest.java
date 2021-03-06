package com.jihwan.book.webservice.web.domain.posts;

import com.jihwan.book.webservice.domain.posts.Posts;
import com.jihwan.book.webservice.domain.posts.PostsRepository;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }
    @Test
    public void 게시글저장_불러오기(){
        //Given
        String title = "테스트게시글";
        String content = "테스트본문";

        postsRepository.save(Posts.builder()
                .title(title)
                        .content(content)
                        .author("kiang@naver.com").build());
        // When
        List<Posts> postsList = postsRepository.findAll();

        //Then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2022,3,15,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());
        Assertions.assertThat(posts.getCreatedDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
