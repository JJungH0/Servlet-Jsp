package com.nhnacademy.chap02;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequest {
    private final FilterChain filterChain = new FilterChain();

    public HttpRequest() {
        initFilter();
    }

    public void doRequest(Request request) {
        log.info("{} -> doFilter 실행",request.getPath());
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
