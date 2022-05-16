package com.jihwan.book.webservice.service;

import com.jihwan.book.webservice.domain.posts.Posts;
import com.jihwan.book.webservice.domain.posts.PostsRepository;
import com.jihwan.book.webservice.web.dto.PostsListResponseDto;
import com.jihwan.book.webservice.web.dto.PostsResponseDto;
import com.jihwan.book.webservice.web.dto.PostsSaveRequestDto;
import com.jihwan.book.webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }
    @Transactional
    public PostsResponseDto findByid(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    /*
    readOnly를 추가하면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도 개선 그래서 등록, 수정, 삭제 기능이
    전혀 없는 서비스 메소드에서 사용하는 것을 추천
     */
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // 람다식을 이용한 것 .map(posts -> new PostListResponseDto(posts))
                .collect(Collectors.toList());
    }
    // postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드

    @Transactional
    public void delete(Long id){
        Posts posts =postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));
        postsRepository.delete(posts);

    }
}
