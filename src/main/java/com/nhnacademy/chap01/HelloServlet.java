package com.nhnacademy.chap01;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/hello")
public class HelloServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//        resp.setContentType("text/html; charset=UTF-8");
//        resp.setCharacterEncoding("utf-8");
        try(PrintWriter pw = new PrintWriter(resp.getWriter())) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
                pw.println("<head>");
                    pw.println("<meta charset='utf-8'>");
                pw.println("</head>");
                pw.println("<body>");
                    pw.println("<h1>hello servlet!</h1>");
                    pw.println("<h1>안녕 서블릿!</h1>");
                pw.println("</body>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("before init!");
        super.init(config);
    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        log.info("before service!");
        super.service(req,resp);
    }

    @Override
    public void destroy() {
        log.info("before destroy!");
        super.destroy();
    }

}
