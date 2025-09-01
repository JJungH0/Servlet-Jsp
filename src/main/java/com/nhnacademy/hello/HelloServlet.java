package com.nhnacademy.hello;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        /**
         * 응답 데이터 문자 인코딩 UTF-8 지정
         */
        resp.setCharacterEncoding("utf-8");

        /**
         * 클라이언트(브라우저)로 응답 보낼 Writer 준비
         */
        try (PrintWriter pw = resp.getWriter()) {
            /**
             * 응답 HTML 문서 작성
             */
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
                pw.println("<head>");
                    pw.println("<meta charset='utf-8'>");
                pw.print("</head>");
                pw.println("<body>");
                    pw.print("<h1> Hello Servlet! </h1>");
                    pw.println("<h1> 안녕 서블릿! </h1>");
                pw.print("</body>");
            pw.println("</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
