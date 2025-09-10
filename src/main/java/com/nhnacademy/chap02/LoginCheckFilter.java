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

        String uri = req.getRequestURI();

        if (!excludeUrls.contains(uri)) {
            HttpSession session = req.getSession(false);
            if (Objects.isNull(session)) {
                resp.sendRedirect("/login.html");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
