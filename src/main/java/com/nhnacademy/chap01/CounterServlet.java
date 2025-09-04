package com.nhnacademy.chap01;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(value = "/counter",
        initParams = {@WebInitParam(name = "counter",
                value = "100")})

public class CounterServlet extends HttpServlet {
    private long counter;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        counter++;
        try (PrintWriter pw = new PrintWriter(resp.getWriter())) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
                pw.println("<head>");
                    pw.println("<meta charset='utf-8'");
                pw.println("</head>");
                pw.println("<body>");
                    pw.printf("<h1> %d </h1>\n",counter);
                pw.println("</body>");
            pw.println("</html>");
        } catch (IOException e) {

        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        counter = Optional.ofNullable(config.getInitParameter("counter"))
                .map(Long::parseLong)
                .orElse(0L);
    }


}
