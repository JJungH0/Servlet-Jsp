package com.nhnacademy.chap02;

public class ChainMain {

    public static void main(String[] args) {
        Request myPageRequest = new Request("/mypage");
        myPageRequest.put("member", Member.createUser("junghwan", "최정환", "12324"));

        Request adminPageRequest = new Request("/admin");
        adminPageRequest.put("member", Member.createAdmin("admin", "관리자", "1234"));

        Request orderPageRequest = new Request("/order");
        orderPageRequest.put("member", Member.createManger("anonymous", "아무개", "1234"));

        Request mainPageRequest = new Request("/main");
        orderPageRequest.put("member", Member.createManger("anonymous", "아무개", "1234"));


        System.out.println("################## /myPage 요청 ##################");
        HttpRequest httpRequest1 = new HttpRequest();
        httpRequest1.doRequest(myPageRequest);

        System.out.println("################## /adminPage 요청 ##################");
        HttpRequest httpRequest2 = new HttpRequest();
        httpRequest2.doRequest(adminPageRequest);


        System.out.println("################## /orderPage 요청 ##################");
        HttpRequest httpRequest3 = new HttpRequest();
        httpRequest3.doRequest(orderPageRequest);

        System.out.println("################## /mainPage 요청 ##################");
        HttpRequest httpRequest4 = new HttpRequest();
        httpRequest4.doRequest(mainPageRequest);
    }
}
