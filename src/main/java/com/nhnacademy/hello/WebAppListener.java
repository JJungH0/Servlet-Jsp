package com.nhnacademy.hello;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebAppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("웹 어플리케이션 실행 -> {}",event.getServletContext().getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        /**
         * 종료 절차에서 로깅 서브시스템이 먼저 내려가거나 플러시가 끝나기 전에
         *  클래스 로더가 언로드됨
         * 그 뒤에 ServletContextListener.contextDestroyed 가 호출되면서
         *  log.info가 호출되지만, 로거가 이미 비활화되어 출력이 유실
         * 반면 println은 JVM 표준 출력 스트림이라 남아있어 콘솔에 찍힘
         */

        /**
         * 톰캣 종료 순서 :
         * 커넥터 정리 -> 웹앱 언로딩(=클래스로더 언로드, 리스너/서블릿 destroy 호출) -> 리소스 정리
         * 로깅 프레임워크는 웹앱 클래스 로더 하위에 로드되거나 비동기 웹렌더를 사용하면,
         *  종료 타이밍에 버퍼가 남거나 로거가 먼저 종료될 수 있음
         */
//        log.info("웹 어플리케이션 종료");
        System.out.println("웹 어플리케이션 종료");
    }
}
