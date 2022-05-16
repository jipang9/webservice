package com.jihwan.book.webservice.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository <Posts, Long>{

    @Query("select p from Posts p order by  p.id desc")
    List<Posts>findAllDesc();
    /*
    // 데이터의 조회는 다양한 조건으로 인해 Entity 클래스만으로 처리하기가 어려워 조회용 프레임워크를 추가로 사용함. (ex-querydsl, jooq, MyBatis)
    // 이 세가지의 예시를 통해 조회하고, 등록/수정/삭제는 SpringDataJPa를 통해 진행 (저자는 querydsl 추천)
    // 추천하는 이유 1. 타입의 안정성이 보장됨 ==> 단순한 문자열로 쿼리를 생성하는 것이 아니라, 메소드 기반으로 쿼리를 생성해 오타나 존재하지 않는 컬럼명을
                                                   명시할경우 IDE에서 자동으로 검출되며 Jooq에서는 지원하나 MyBatis에서는 지원하지 않음.
                     2. 국내의 많은 회사에서 사용 ==> JPA를 적극적으로 사용하는 회사에서는 Querydsl을 적극적으로 사용함.
                     3. 레퍼런스가 多  ==> 문제가 발생했을 때 질문하고, 답변을 들을 수 있는 기회가 열려있음.
    */
}
