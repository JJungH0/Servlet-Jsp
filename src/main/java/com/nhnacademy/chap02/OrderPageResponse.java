package com.nhnacademy.chap02;

public class OrderPageResponse implements Response{

    @Override
    public void doResponse(Request request) {
        System.out.println("###### response:OrderPageResponse #####");
        Member member = (Member) request.get("member");
        System.out.println("아이디:" + member.getId());
        System.out.println("이름:" + member.getName());
        System.out.println("등급:" + member.getRole());
        System.out.println("주문내역: 소파 1개, 냉장고 1개");
        System.out.println("do something ... Order ...");
    }
}
