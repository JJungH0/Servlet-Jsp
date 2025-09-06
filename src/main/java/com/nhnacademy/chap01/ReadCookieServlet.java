package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@WebServlet
        (value = "/read-cookie")
public class ReadCookieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie cookie = CookieUtils.getCookie(req, "locale");
        Cookie myNameCookie = CookieUtils.getCookie(req, "myName");
        Cookie myAgeCookie = CookieUtils.getCookie(req, "myAge");

        if (Objects.isNull(cookie) || Objects.isNull(myNameCookie) || Objects.isNull(myAgeCookie)) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Cookie not Found");
        }

        String locale = cookie.getValue();
        String myName = myNameCookie.getValue();
        String myAge = myAgeCookie.getValue();

        String helloValue = ResourceBundle.getBundle("message", new Locale(locale))
                .getString("hello");

        resp.setContentType("text/plain; charset=UTF-8");

        try (PrintWriter pw = resp.getWriter()) {
            pw.println(helloValue);
            pw.println(myName);
            pw.println(myAge);
        }
    }
}
