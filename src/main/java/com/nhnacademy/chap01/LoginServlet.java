package com.nhnacademy.chap01;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


@Slf4j
@WebServlet(
        value = "/login",
        initParams = {
                @WebInitParam(name = "id", value = "admin"),
                @WebInitParam(name = "pwd", value = "1234")
        }
)
public class LoginServlet extends HttpServlet {

    private String initParamId;
    private String initParamPwd;

    @Override
    public void init(ServletConfig config) {
        /**
         * 서블리 초기화 시점 (=Init)에 web.xml 또는 애노테이션에서 준 init-param값을 변수에 저장
         * 여기서는 애노테이션에서 준 init-param값 저장
         */
        initParamId = config.getInitParameter("id");
        initParamPwd = config.getInitParameter("pwd");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /**
         * req.getSession(false) :
         * -> 현재 요청에 연결된 세션을 가져옴 없다면 Null
         * If :
         * -> session이 존재하지 않거나, Id라는 속성 (= 로그인 상태)가 없다면
         *      로그인 페이지로 리다이렉트
         */
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("id"))) {
            resp.sendRedirect("/login.html");
        } else {
            log.info("session Id: {}", session.getId());
            resp.setContentType("text/html; charset=UTF-8");
            try (PrintWriter pw = resp.getWriter()) {
                pw.println("<!DOCTYPE html>");
                pw.println("<html>");
                pw.println("<body>");
                pw.println("login success : id =" + session.getAttribute("id") + "<br/>");
                pw.println("<a href='/logout'> logout</a>");
                pw.println("<a href='/'> home</a>");
                pw.println("</body>");
                pw.println("</html>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        /**
         * req.getParameter :
         * -> 로그인 폼(login.html)에서 전성된 파라미터(id, pwd)를 꺼냄
         */
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");

        /**
         * 입력값이 초기 파라미터 (initParamId / initParamPwd)와 일치하면 :
         * -> 세션을 새로 만들거나 기존 세션을 가져옴 (getSession())
         * -> 세션에 id 속성을 저장 -> 이후 로그인 상태 유지 근거
         * -> /login (= 즉 doGet)으로 리다이렉트 -> 로그인 성공 화면 출력
         */
        if (initParamId.equals(id) && initParamPwd.equals(pwd)) {
            HttpSession session = req.getSession();
            session.setAttribute("id", id);
            resp.sendRedirect("/login");
        } else {
            log.error("아이디 / 패스워드가 일치하지 않습니다.");
            RequestDispatcher rd = req.getRequestDispatcher("/login.html");
            rd.forward(req, resp);
            log.error("id {}",id);
//            resp.sendRedirect("/login.html");
        }
    }

}
