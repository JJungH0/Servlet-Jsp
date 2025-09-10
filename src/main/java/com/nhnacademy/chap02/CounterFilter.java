package com.nhnacademy.chap02;

import com.nhnacademy.chap01.CounterUtils;
import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(filterName = "counterFilter",
urlPatterns = "/*")
public class CounterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 현재 요청이 속한 웹 애플리케이션의
         *  ServletContext를 얻어서 "counter"라는 이름의
         *   속성을 증가시킴
         */
        CounterUtils.increaseCounter(servletRequest.getServletContext());
        /**
         * 다음 필터 혹은 서블릿으로 요청을 넘겨 실제 처리를 진행
         */
        filterChain.doFilter(servletRequest, servletResponse);
        /**
         * 후처리 시점에 현재 counter값을 로깅
         *  해당 시점의 값은 이미 요청 반영 후의 값 (= 증가된 값)
         */
        log.info("counter: {}", servletRequest.getServletContext().getAttribute("counter"));
    }
}
