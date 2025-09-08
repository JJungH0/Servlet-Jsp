package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Objects;

@WebServlet(
        value = "/logout"
)
public class LogoutServlet extends HttpServlet {


    /**
     * req.getSession(false) :
     * -> 기존 세션만 가져온다. 없다면 Null
     * session.invalidate() :
     * -> 만약 세션이 존재한다면 서버 측 세션 상태를 제거
     * Cookie.Utils (요청에 해당 하는 쿠키 찾기) :
     * -> setMaxAge(0) = 즉시 삭제
     * -> setValue("") = 혹시라도 삭제가 실패했을 시 민감 정보가 남지 않도록 값을 비움
     *                 = 일부 환경/레거시 브라우저에서 쿠키를 덮어쓸때 값이 함께 갱신되어야
     *                    동작히 확실한 경우가 있어 관용적으로 빈 값으로 덮어씀
     *                 = 더는 쓸 데이터가 없다는 의도를 명확히 표현
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // 세션이 존재하면 가져오고 없다면 Null
        HttpSession session = req.getSession(false);

        // 세션이 존재 및 살아있다면
        if (Objects.nonNull(session)) {
            session.invalidate();
        }

        Cookie cookie = CookieUtils.getCookie(req, "JSESSIONID");
        if (Objects.nonNull(cookie)) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        resp.sendRedirect("/login.html");

    }
}
