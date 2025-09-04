package com.nhnacademy.chap01;


import jakarta.servlet.ServletContext;

import java.util.Optional;

public final class CounterUtils {
    private CounterUtils() {
        throw new IllegalArgumentException("Utility Class");
    }

    public static void increaseCounter(ServletContext context) {
        Long counter = (Long)Optional.ofNullable(context.getAttribute("counter"))
                .orElse(0L);
        counter++;
        context.setAttribute("counter", counter);
    }
}
