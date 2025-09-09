package com.nhnacademy.hello;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter(
//        filterName = "LoggingFilter", urlPatterns = "/*"
//)
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * HTTP 전용 API 사용을위한 다운 캐스팅
         * HttpServletRequest는 ServletRequest를 상속받고 있음.
         */
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        /**
         * 전처리 작업 :
         * doFilter를 넘기기 전임
         */
        long strat = System.currentTimeMillis();

        try{
            /**
             * 해당 코드에서 다음 필터/서블릿으로 넘김 (= 여기서 실제 처리 진행)
             */
            filterChain.doFilter(servletRequest, servletResponse);
        }finally{
            /**
             * 후처리 작업 :
             * doFilter를 넘김
             */
            long end = System.currentTimeMillis() - strat;
            log.info("[LOG] {} {} ({} ms)\n",request.getMethod(),request.getRequestURI(),end);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
