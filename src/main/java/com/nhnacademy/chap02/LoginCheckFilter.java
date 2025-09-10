package com.nhnacademy.chap02;

import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class LoginCheckFilter implements Filter {

    private final Set<String> excludeUrls = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /**
         * web.xml에서 정의한 초기 init값을 가져옴
         * -> "," 기준으로 쪼갠후
         *      앞뒤 공백을 제거하여 Set에 저장 (= 중복값 없음 )
         */
        String urls = filterConfig.getInitParameter("exclude-urls");
        log.info("exclude-urls:{}", urls);

        Arrays.stream(urls.split(","))
                .map(String::trim)
                .forEach(excludeUrls::add);

        log.info("result-urls:{}",excludeUrls);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        /**
         * 현재 URL
         */
        String uri = req.getRequestURI();

        /**
         * 제외 목록에 없다면 (= 검사 대상)
         */
        if (!excludeUrls.contains(uri)) {
            /**
             * 세션은 새로 만들지 않고 기존 값만 검사 없다면 Null
             */
            HttpSession session = req.getSession(false);
            /**
             * 세션이 존재 하지 않다면 로직 수행
             */
            if (Objects.isNull(session)) {
                RequestDispatcher rd = req.getRequestDispatcher("/login");
                rd.forward(servletRequest,servletResponse);
//                resp.sendRedirect("/login");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
