package com.nhnacademy.chap01;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@WebServlet(value = "/now",
loadOnStartup = 2)
public class NowServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        CounterUtils.increaseCounter(getServletContext());

        log.info("NowServlet doGet called. uri={}, remote={}",
                req.getRequestURI(), req.getRemoteAddr());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String nowDateTimeToString = now.format(df);

        try (PrintWriter pw = new PrintWriter(resp.getWriter())) {
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
                pw.println("<head>");
                    pw.println("<meta charset='utf-8'>");
                pw.println("</head>");
                pw.println("<body>");
                    pw.println("<h1> 현재 시간 </h1>");
                    pw.println(String.format("<h1> %s </h1>",nowDateTimeToString));
            /**
             * 전역 보관함의 counter 현재 값 출력
             */
            pw.printf("<h1> counter : %d </h1>\n",getServletContext().getAttribute("counter"));
                pw.println("</body>");
            pw.println("</html>");
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
    public void destroy() {
        log.info("before destroy!");
    }
}
