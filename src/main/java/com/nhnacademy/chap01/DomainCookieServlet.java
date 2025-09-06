package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
@WebServlet(
        value = "/domain-cookie/*"
)
public class DomainCookieServlet extends HttpServlet {
    private static final String COOKIE_NAME = "testCookie";
    /**
     * /domain-cookie/more/write로 진입시
     * Path를 /domain-cookie/more로 재현해서 굽는 테스트
     */
    private static final String MORE_PATH = "/domain-cookie/more/write";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();

        log.error("requestUri:{}", requestURI);
        
        if (requestURI.endsWith("/read")) {
            readCookie(req, resp);
        } else if (requestURI.endsWith("/write")) {
            writeCookie(req, resp);
        } else {
            show(req, resp);
        }
    }

    private void show(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html; charset=UTF-8");

        try (PrintWriter pw = resp.getWriter()) {
            pw.println("<html>");
            pw.println("<head><title>cookie test</title></head>");
            pw.println("<body>");
            pw.println("<ul>");
            /**
             * a.com에서 쿠키 굽기 :
             * domain=a.com, path=/ (write)
             */
            pw.println("<li><a href='http://a.com:8080/domain-cookie/write?domain=a.com'>" +
                    "set cookie: domain=a.com, path=/</a></li>");

            /**
             * b.com에서 쿠키 굽기 :
             * domain=b.com, path=/ (write)
             */
            pw.println("<li><a href='http://b.com:8080/domain-cookie/writer?domain=b.com'>" +
                    "set cookie: domain=b.com, path=/</a></li>");

            /**
             * a.com에서 쿠키 읽기 :
             * domain=a.com (read)
             */
            pw.println("<li><a href='http://a.com:8080/domain-cookie/read'>" +
                    "get cookie: domain=a.com</a><br /></li>");

            /**
             * b.com에서 쿠키 읽기 :
             * -> 다른 도메인이므로 보이면 안됨
             * domain=b.com (read)
             */
            pw.println("<li><a href='http://b.com:8080/domain-cookie/read'>" +
                    "get cookie: domain=b.com</a><br /></li>");


            /**
             * 1.a.com에서 쿠키 읽기 :
             * 하위 도메인 (보여야 함, Domain=a.com)
             */
            pw.println("<li><a href='http://1.a.com:8080/domain-cookie/read'>" +
                    "get cookie: domain=1.a.com</a><br /></li>");

            /**
             * 2.a.com에서 쿠키 읽기 :
             * 하위 도메인 (보여야 함, Domain=a.com)
             */
            pw.println("<li><a href='http://2.a.com:8080/domain-cookie/read'>" +
                    "get cookie: domain=2.a.com</a><br /></li>");

            pw.println("</ul>");
            pw.println("</body>");
            pw.println("</html>");
        } catch (IOException e) {
            log.error("show-cookie error : {}",e.getMessage(), e);
        }
    }

    private void readCookie(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain; charset=UTF-8");

        /**
         * 쿠키 배열은 null 일수있으므로 항시 Null 체크 필수
         * 해당 코드에서는 아무런 처리없이 종료
         */
        if (Objects.isNull(req.getCookies())) {
            return;
        }

        /**
         * 모든 쿠키를 나열 :
         * -> 해당 요청에 붙어서 온 쿠키만
         */
        try (PrintWriter pw = resp.getWriter()) {
            for (int i = 0; i < req.getCookies().length; i++) {
                Cookie cookie = req.getCookies()[i];
                pw.println(cookie.getName() + ":" + cookie.getValue());
            }
        } catch (IOException e) {
            log.error("read-cookie error : {}",e.getMessage(), e);
        }
    }

    private void writeCookie(HttpServletRequest req, HttpServletResponse resp) {

        /**
         * 쿼리 파라미터로 받은 Domain값 추출 :
         */
        String domain = req.getParameter("domain");

        /**
         * 요청 URI가 /domain-cookie/more/write 라면
         * -> /domain-cookie/more로 변경
         * -> 아니라면 "/"로 설정 (= Path 접두사 실험)
         */

        String path = MORE_PATH.equals(req.getRequestURI())
                ? MORE_PATH.replace("/write", "")
                : "/";

        /**
         * 쿠키의 현재 값은 "URL?쿼리"
         * 쿼리가 NULL일 수 있으니 NPE 발생 가능성 있음.(Null 체크)
         */

        String value = Objects.isNull(req.getQueryString())
                ? req.getRequestURL().toString()
                : req.getRequestURL().append("?")
                .append(req.getQueryString()).toString();

        Cookie newCookie = new Cookie(COOKIE_NAME, value);
        newCookie.setDomain(domain);
        newCookie.setPath(path);
        newCookie.setHttpOnly(true);

        resp.addCookie(newCookie);

        log.error("cookieName: {}", newCookie.getName());
        log.error("cookieValue: {}", newCookie.getValue());
        log.error("cookieDomain: {}", newCookie.getDomain());

        show(req,resp);
    }
}
