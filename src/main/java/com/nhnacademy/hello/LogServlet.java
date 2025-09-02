package com.nhnacademy.hello;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/log")
@Slf4j
public class LogServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext context = getServletContext();

        context.log("LogServlet 실행됨!");
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            context.log("에러 발생", e);
        }
        resp.setContentType("text/plain");

        resp.getWriter().println("로그 출력 완료");
    }
}
