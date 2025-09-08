package com.nhnacademy.chap01;

import jakarta.servlet.RequestDispatcher;
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
            cookie.setPath("/");
            resp.addCookie(cookie);
        }
        /**
         * 로그아웃시 어떤 게 적절한가?
         * -> 보통은 리다이렉트가 적절
         * -> PRG 패턴 (Post||Redirect||Get)과 동일한 이유로,
         *      새로고침 시 중복 동작을 피하고 URL도 로그인 페이지로 명확히 바뀜
         * -> 포워드는 서버 내부 이동이라 URL이 /logout으로 남고, 사용자가 새로고침
         *      하면 다시 로그아웃 동작을 유발할 수 있어서 UX가 좋지 않음.
         */
        resp.sendRedirect("/login.html");
//        RequestDispatcher rd = req.getRequestDispatcher("/logout.html");
//        rd.forward(req, resp);

    }
}
