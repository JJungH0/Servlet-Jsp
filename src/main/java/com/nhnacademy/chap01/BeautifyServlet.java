package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
/**
 * 컨텍스트 경로 뒤의 /beautify로 매핑
 */
@WebServlet(value = "/beautify",
        loadOnStartup = 3)

public class BeautifyServlet extends HttpServlet {

    /**
     * 브라우저가 POST로 보낼 때 호출됨
     * -> 컨테이너가 service()에서 doPost()로 디스패치
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String html = req.getParameter("testsubmit");
//        resp.setContentType("text/plain");
//        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain; charset=UTF-8");

        try (PrintWriter pw = new PrintWriter(resp.getWriter())) {
            pw.println(Jsoup.parse(html));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
