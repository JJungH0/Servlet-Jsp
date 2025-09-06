package com.nhnacademy.chap01;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    private CookieUtils() {
        throw new RuntimeException("유틸 클래스입니다~");
    }

    public static Cookie getCookie(HttpServletRequest req, String name) {
        return Optional.ofNullable(req.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(name))
                        .findFirst())
                .orElse(null);
    }
}
