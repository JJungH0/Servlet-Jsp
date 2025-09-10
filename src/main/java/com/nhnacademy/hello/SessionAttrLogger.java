package com.nhnacademy.hello;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@WebListener
public class SessionAttrLogger implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        log.info("세션 속성 추가 -> {} = {}", event.getName(), event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        log.info("세션 속성 제거 -> {}",event.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        log.info("세션 속성 변경 -> {}",event.getName());
    }
}
