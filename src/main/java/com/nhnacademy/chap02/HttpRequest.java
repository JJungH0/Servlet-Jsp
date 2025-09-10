package com.nhnacademy.chap02;

import lombok.extern.slf4j.Slf4j;

/**
 * HttpRequest :
 * -> 해당 클래스가 생성될 때 생성자쪽에 정의해놓은 initFilter()를 통해
 *      체인을 구성함
 */
@Slf4j
public class HttpRequest {
    private final FilterChain filterChain = new FilterChain();

    public HttpRequest() {
        initFilter();
    }

    public void doRequest(Request request) {
        log.info("{} -> doFilter 실행",request.getPath());
        /**
         * 필터들을 추가 후 reset()을 호출하여서
         * 커서를 맨앞으로 이동
         */
        filterChain.reset();
        filterChain.doFilter(request);
    }

    private void initFilter() {
        filterChain.addFilter(new MyPageFilter());
        log.info("MyPage 필터 추가");
        filterChain.addFilter(new AdminPageFilter());
        log.info("AdminPage 필터 추가");
        filterChain.addFilter(new OrderPageFilter());
        log.info("OrderPage 필터 추가");

    }
}
