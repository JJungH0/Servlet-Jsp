package com.nhnacademy.hello;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

//@WebFilter(
//        filterName = "AuthFilter", urlPatterns = "/*",
//        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
//
//)
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        boolean publicPath = uri.startsWith("/login") || uri.equals("/login.html");

        if (publicPath) {
            filterChain.doFilter(req,resp);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = Objects.nonNull(session) && session.getAttribute("id") != null;
        if (!loggedIn) {
            req.setAttribute("msg", "로그인이 필요합니다.");
            RequestDispatcher rd = req.getRequestDispatcher("/login");
            rd.forward(req, resp);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
