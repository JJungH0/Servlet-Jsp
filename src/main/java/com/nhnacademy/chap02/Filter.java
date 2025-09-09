package com.nhnacademy.chap02;

public interface Filter {
    void doFilter(Request request, FilterChain filterChain);
}
