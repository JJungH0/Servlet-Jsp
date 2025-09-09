package com.nhnacademy.chap02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FilterChain {
    private final List<Filter> filters = new LinkedList<>();
    private Iterator iterator;

    public void addFilter(Filter filter) {
        filters.add(filter);
        iterator = filters.iterator();
    }

    public void doFilter(Request request) {
        if (iterator.hasNext()) {
            Filter next = (Filter) iterator.next();
            next.doFilter(request, this);
        } else {
            if (request.getPath().equals("/mypage")) {
                new MyPageResponse().doResponse(request);
            } else if (request.getPath().equals("/admin")) {
                new AdminPageResponse().doResponse(request);
            } else if (request.getPath().equals("/order")) {
                new OrderPageResponse().doResponse(request);
            } else {
                System.out.println("존재하지 않는 페이지!");
            }
        }
    }
}
