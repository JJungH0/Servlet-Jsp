package com.nhnacademy.chap02;

import java.util.Objects;

public class MyPageFilter implements Filter{
    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        if (request.getPath().equals("/mypage")) {
            Member member = (Member) request.get("member");
            if (Objects.nonNull(member)) {
                if (member.hasRole(Member.Role.USER)) {
                    System.out.println("path: " + request.getPath() + " : member role has User -> True");
                    filterChain.doFilter(request);
                } else {
                    System.out.println("path: " + request.getPath() + " : member role has User -> False");
                }
            } else {
                System.out.println("MyPageCheckFilter : 다음 필터로 넘김!");
                filterChain.doFilter(request);
            }
        } else {
            filterChain.doFilter(request);
        }
    }
}
