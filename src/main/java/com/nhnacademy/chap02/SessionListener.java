package com.nhnacademy.chap02;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener {
    /**
     * 그럼 왜 세션에서는 원자적 연산을 사용할까? :
     * -> 세션은 "사용자 범위"지만, 만약 같은 사용자가
     *      브라우저 탭 여러 개로 동시에 요청을 보내면 동시 접근이 가능하다.
     *          세션 속성에 가변 객체를 넣고 수정한다면 역시 동기화가 필요하기 때문에
     *              원자적 연산을 사용한다.
     */

    /**
     * AutomicInteger :
     * -> 원자적 읽기-수정-쓰기 (= CAS, Compare-And-Set)
     * -> incrementAndGet, getAndIncrement, ... 같은 메서드는 내부적으로
     *      "현재 값이 내가 방금 읽은 값과 같다면 새 값으로 바꿔라"라는 CAS 연산을 사용
     * -> 여러 스레드가 동시에 증가를 시도해도, 오직 하나만 성공하고 나머지는 최신 값을
     *      다시 읽어 재시도함 (= 잠금 없이 반복), 그래서 "읽고 -> +1 -> 다시 저장"이
     *          하나의 불가분한 단계처럼 보장
     */
    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
//        HttpSessionListener.super.sessionCreated(se);
        int sessionCount = atomicInteger.incrementAndGet();
        log.info("Session-Create : {}",sessionCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
//        HttpSessionListener.super.sessionDestroyed(se);
        int sessionCount = atomicInteger.decrementAndGet();
        log.info("Session-Destroy : {}",sessionCount);
    }
}
