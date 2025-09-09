package com.nhnacademy.chap02;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


/**
 * Request 생성자 :
 * 1. 생성 주도권을 프레임워크 || 패키지 || 상속 계층 내부로 제한
 * 2. 외부에서는 상속만 허용하고, 직접 생성은 막는다.
 * 이점 :
 * -> Request 생성 경로를 내부로 제한하면 "잘못된 요청이 체인에 들어오는 것"
 *      을 구조적으로 차단하고, 검증 && 보안 && 관측 && 유지보수성을 모두 끌어올림
 */
public class Request {
    @Getter
    private final String path;
    private final Map<String, Object> data = new HashMap<>();

    protected Request(String path) {
        this.path = path;
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

}
