package com.jihwan.book.webservice.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void  롬복_기능_테스트(){
        //Given
        String name = "test";
        int amount = 1000;

        //When
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //Then
        // asserthat은 테스트 검증 라이브러리의 검증 메서드로 검증하고 싶은 대상을 메소드 인자로 받는다.
        // isEqualTo는 동등비교 메서드로 assertThat에 있는 값과 비교해 같을 때만 성공한다.
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

    }

}
