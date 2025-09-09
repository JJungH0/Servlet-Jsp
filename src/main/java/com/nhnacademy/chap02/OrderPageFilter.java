package com.nhnacademy.chap02;

import java.util.Objects;

public class OrderPageFilter implements Filter{
    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        if (request.getPath().equals("/order")) {
            Member member = (Member) request.get("member");
            if (Objects.nonNull(member)) {
                if (!member.hasRole(Member.Role.NONE)) {
                    System.out.println("path: " + request.getPath() + " : member role has Not None -> True");
                    filterChain.doFilter(request);
                } else {
                    System.out.println("path: " + request.getPath() + " : member role has Not None -> False");
                }
            } else {
                System.out.println("OrderPageCheckFilter : 다음 필터로 넘김!");
                filterChain.doFilter(request);
            }
        } else {
            filterChain.doFilter(request);
        }
    }
}
