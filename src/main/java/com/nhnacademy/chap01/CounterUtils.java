package com.nhnacademy.chap01;


import jakarta.servlet.ServletContext;

import java.util.Optional;

public final class CounterUtils {
    /**
     * 유틸클래스이므로 인스턴스화 금지
     */
    private CounterUtils() {
        throw new IllegalArgumentException("Utility Class");
    }

    public static void increaseCounter(ServletContext context) {
        /**
         * 전달받은 공유 context인자에서 "counter"라는 값을 꺼냄
         * -> 없을시 0L로 기본 설정
         * -> 값 하나 증가시킨후 다시 전역 보관함에 저장
         */
        Long counter = (Long)Optional.ofNullable(context.getAttribute("counter"))
                .orElse(1000L);
        counter++;
        context.setAttribute("counter", counter);
    }
}
