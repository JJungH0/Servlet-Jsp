package com.nhnacademy.chap02;

import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

//@WebFilter(filterName = "characterEncodingFilter",
//        urlPatterns = "/*",
//        initParams = @WebInitParam(
//                name = "encoding",
//                value = "UTF-8"
//        ))
public class CharacterEncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);
    }
    /**
     * 질의응답 :
     * doFilter(..)를 호출하지 않으면 요청 흐름이 다음 필터 / 서블릿으로 넘어가지 않음
     *  인코딩을 설정해도 그 값을 실제로 읽어줄 대상(= 다음 필터나 서블릿)이 없음
     *   결과적으로 파라미터 해석도, 컨트롤러/서블릿 처리도 최종 응답도 진행이 안됨
     */
}
