package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(
        value = "/set-cookie"
)
public class SetCookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String locale = req.getParameter("locale");
        if (Objects.isNull(locale)) {
            locale = "ko";
        }

        Cookie cookie = new Cookie("locale", locale);
        cookie.setMaxAge(-1); // 브라우더 닫을 때 삭제
        cookie.setPath("/"); // 전체 애플리케이션 적용
        resp.addCookie(cookie);
        try (PrintWriter pw = resp.getWriter()) {
            pw.println("OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
