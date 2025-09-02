package com.nhnacademy.chap01;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * Servlet 인터페이스 :
 * -> 서블릿의 생명주기와 동작을 정의하는 핵심 계약
 * -> 서블릿 컨테이너(Ex: Tomcat)가 해당 메서드를 호출하여
 *  서블릿 초기화, 요청 처리, 자원 정리 담당
 */
public interface Servlet {

    /**
     * init :
     * -> 서블릿이 최초 로딩될 때 딱 한 번 호출됨
     * -> 초기화 파라미터와 ServletContext 등에 접근할 수 있는
     *      ServletConfig를 전달받아 초기 준비
     *          (리소스 로딩, 커넥션 풀 참조)등을 수행
     * @throws ServletException
     * -> 던지면 서블릿은 서비스에 들어가지 않음.
     */
    public void init(ServletConfig config)
            throws ServletException;

    /**
     * service :
     * -> 실제 클라이언트 요청이 들어올때 마다 호출되어 요청을 처리하고 응답 작성
     * -> I/O 처리 중 문제가 생기면 IOException, 처리 로직 중 문제가 생기면
     *      ServletException을 던짐
     * -> 일반적으로 HttpServlet을 사용할 경우 이 메서드는 doGet/doPost
     *      등으로 분기되지만, 인터페이스 차원에서는 프로토콜에 중립적인 요청/응답 추상화를 제공
     * -> 다중 스레드 환경에서 동시에 호출될 수 있으므로, 공유 상태는 반드시 스레드를
     *      안전하게 관리해야함.
     * @throws IOException
     */
    public void service(ServletRequest req, ServletResponse resp)
            throws ServletException, IOException;

    /**
     * destroy :
     * -> 서블릿이 컨테이너에서 내려가거나 재배포될 때 한 번 호출됨
     * -> 열어둔 자원(= 파일 핸들, 스레드, DB연결)등을 정리/해제할때 사용
     */
    public void destroy();

    /**
     * getServletConfig() :
     * -> 초기화 파라미터 조회, ServletContext 접근에 활용
     * @return init에서 전달받은 ServletConfig 반환
     */
    public ServletConfig getServletConfig();

    /**
     * getServletInfo() :
     * -> 관리 / 모니터링 도구가 정보를 표시할 때 사용함
     * @return 서블릿의 설명, 버전, 저작자 등 메타정보 문자열을 반환
     */
    public String getServletInfo();
}
