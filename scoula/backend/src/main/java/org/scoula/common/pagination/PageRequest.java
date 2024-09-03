package org.scoula.common.pagination;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 생성자의 외부 접근 막음
public class PageRequest {
    private int page;
    private int amount;

    //    기본 생성자에서 기본 페이지 1개, 기본 데이터 개수 10개로 초기화
    public PageRequest(){
        page = 1; // 쪽수
        amount = 10; // 한 페이지에 들어갈 게시글 개수
    }

    //    팩토리 메소드를 사용해서 PageRequest 생성자 사용
    public static PageRequest of(int page, int amount){
        return new PageRequest(page, amount);
    }

    //    해당 페이지에서 데이터를 가져올 때 데이터의 시작 위치 계산
    public int getOffset(){
        return (page - 1) * amount;
    }
}