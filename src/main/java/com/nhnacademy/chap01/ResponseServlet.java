package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


@WebServlet(value = "/resp")
@Slf4j
public class ResponseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        /**
         * Buffer Size 설정 :
         * 1024 byte = 1kb
         *  - 1KB 이하의 작은 양의 데이터 전송 시 출력 buffer가 꽉 차지 않아도 바로 전송
         *  - 1KB 이상의 큰 데이터양을 전송할 때는 출력 버퍼가 가득 차기 전까지 데이터를 쌓은 다음에 한 번에 전송
         *  - default bufferSize : 8192 byte = 8KB
         */
        log.info("default bufferSize: {}", resp.getBufferSize());
        resp.setBufferSize(1024);

        resp.setContentType("text/plain; charset=UTF-8");
        try (PrintWriter pw = resp.getWriter()) {
            pw.println("locale = " + req.getLocale());
            pw.println("parameter name = " + req.getParameter("name"));
            /**
             * 버퍼링 된 출력 바이트를 즉시 쓰도록 (= 소켓을 통해서 내보냄) -> 강제
             * client와 연결이 종료됨 -> 아래 로직은 수행이 되더라도 브라우저에 표시안됨
             */
//            pw.flush();
//            pw.close();

            String userId = req.getParameter("userId");
            log.info("userId:{}", userId);
            if (Objects.isNull(userId) || userId.isEmpty()) {
                /**
                 * Response 상태/헤더/바디 모두 초기화 :
                 */
                resp.reset();

//                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "name is empty");
                return;
            }
            String redirect = req.getParameter("redirect");
            if (Objects.nonNull(redirect) && !redirect.isBlank()) {
                resp.sendRedirect(redirect);
                return;
            }

            pw.println("method = " + req.getMethod());
            pw.println("request uri = " + req.getRequestURI());
            /**
             * reset buffer :
             * -> Response Body만 초기화
             */
            resp.resetBuffer();
            pw.println("User-Agent header = " + req.getHeader("User-Agent"));
        } catch (IOException e) {
            log.error("/req : {}", e.getMessage(), e);
        }
    }
}
