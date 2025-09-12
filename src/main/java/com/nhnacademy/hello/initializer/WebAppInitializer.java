package com.nhnacademy.hello.initializer;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Set;

/**
 * 해당 인터페이스-클래스를 "구현 / 상속"한 구체 클래스들을 수집
 */
@HandlesTypes(value =
        {
                jakarta.servlet.http.HttpServlet.class,
                jakarta.servlet.Filter.class,
                jakarta.servlet.ServletContextListener.class,
                jakarta.servlet.http.HttpSessionListener.class
        })
public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        /**
         * web.xml의 <context-param>을 코드로 대체
         * setInitParameter는 "이미 같은 이름이 설정되어 있으면 false반환 + 값 유지"
         * 즉, 중복되면 값이 바뀌지 않으므로 web.xml에서 제거하는게 안전
         */
        servletContext.setInitParameter("url", "https://nhnacademy.com");
        servletContext.setInitParameter("counterFileName", "counter.dat");
    }
}
