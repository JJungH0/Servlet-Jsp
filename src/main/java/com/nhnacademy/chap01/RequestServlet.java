package com.nhnacademy.chap01;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(value = "/req")

public class RequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain; charset=UTF-8");
        /**
         * resp.getWriter() :
         * -> HTTP 응답 바디에 "문자 텍스트"를 쓰기 위한 Writer를 반환
         * -> 컨테이너가 응답의 인코딩(Content-Type의 charset 또는
         *      setCharacterEncoding)과 버퍼링을 적용한 Writer를 제공
         * PrintWriter.println(..) :
         * -> 반환받은 Writer로 HTML 문자열을 작성
         * -> println은 줄바꿈까지 포함해 편리하게 출력
         * -> 최종적으로 해당 데이터가 HTTP 응답 바디로 전송
         */
        try (PrintWriter pw = resp.getWriter()) {
            pw.println("local = " + req.getLocale());
            pw.println("parameter name = " + req.getParameter("userId"));
            pw.println("content type = " + req.getContentType());
            pw.println("content Length = " + req.getContentLength());
            pw.println("method = " + req.getMethod());
            pw.println("servlet path = " + req.getServletPath());
            pw.println("request uri = " + req.getRequestURI());
            pw.println("request url = " + req.getRequestURL());
            pw.println("User-Agent header = " + req.getHeader("User-Agent"));

        } catch (IOException e) {
            log.error("/req : {}", e.getMessage(), e);
        }
    }
}
