package com.nhnacademy.chap01;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Slf4j
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
        log.info("before init!");
        /**
         * 관례 :
         * init(ServletConfig)을 오버라이드했다면 항상 super.init(config) 호출
         */
        super.init(config);
        /**
         * init 안에서만 전달된 config로 값을 읽어 필드(=counter)에 캐싱하고
         * 이후에는 getServletConfig()를 전혀 쓰지않는다면 생략해도 무방
         * 실무적인 방안 :
         * -> 이후 doGet/doPost 등에서 getServletConfig()을 접근하면 null됨
         * -> 향후 유지보수중 한 줄만 추가되어도 바로 NPE 위험
         */

        /**
         * 의미와 동작 흐름 :
         * -> counter.getInitParameter("counter")
         *  -> 서블릿 초기화 파라미터중 "counter"라는 이름의 값을 문자열로 가져옴
         *  -> 설정되어 있지 않으면 null을 반환
         * -> Optional.ofNullable(...)
         *  -> 값이 null일 수도 있으니 Optional로 감싸 null 처리를 간결하게함
         * -> .map(Long::parseLong)
         *  -> 값이 존재하면 문자열을 Long으로 변환
         *  -> 이때 숫자 형식이 아니면 NFE(=NumberFormatEx)이 터짐
         * -> .orElse(0L);
         *  -> 값이 비어 있으면(=초기화 파라미터가 없거나 null)이면 기본값 0L을 사용
         */
        counter = Optional.ofNullable(config.getInitParameter("counter"))
                .map(Long::parseLong)
                .orElse(0L);
    }

    @Override
    public void destroy() {
        log.info("before destroy!");
        super.destroy();
    }


}
