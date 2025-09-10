package com.nhnacademy.chap02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FilterChain {
    private final List<Filter> filters = new LinkedList<>();
    /**
     * 제네릭으로 명시해서 타입캐스팅 불필요
     */
    private Iterator<Filter> iterator;
//    private Iterator iterator;

    /**
     * - filters 리스트에 새 필터를 추가한 직후,
     *  iterator = filters.iterator()로
     *   리스트의 처음부터 다시 순회할 준비
     * - 즉 여러번 호출하면 매번 커서가 맨 앞으로 리셋됨
     * @param filter
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
//        iterator = filters.iterator();
    }

    public void reset() {
        /**
         * 커서 맨앞으로 이동
         */
        iterator = filters.iterator();
    }

    public void doFilter(Request request) {
        if (iterator.hasNext()) {
            Filter next = iterator.next();
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
