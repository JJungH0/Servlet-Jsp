package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
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
