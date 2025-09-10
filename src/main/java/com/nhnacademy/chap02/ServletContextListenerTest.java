package com.nhnacademy.chap02;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebListener
public class ServletContextListenerTest implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /**
         * sce.getServletContext()
         * -> 전역 컨텍스트 접근
         */
        ServletContext context = sce.getServletContext();
        /**
         * context.getInitParameter(...);
         * -> web.xml에서 지정한 키에 해당하는 값(= counter.dat)을 가져옴
         */
        String fileName = context.getInitParameter("counterFileName");
        /**
         * filePath :
         * -> 클래스 출력/리소스 폴더 위치를 가리키는 웹앱 내부 상대 경로 구성
         */
        String filePath = "/WEB-INF/classes/" + fileName;
        /**
         * context.getRealPath(...);
         * -> 위 상대 경로를 실제 파일 시스템 절대 경로로 반환
         * -> exploded 배포(=폴더)에서는 정상 경로가 나옴
         *      환경에 따라 null일수 있음
         */
        String realPath = context.getRealPath(filePath);
        log.info("path: {}", realPath);

        File target = new File(realPath);

        /**
         * 경로에 파일이 존재하면 읽기 진행 :
         * -> 첫 줄을 읽고 Long으로 파싱 진행
         * -> context.setAttribute("counter",c);
         *      전역 보관함에 초기값 저장
         * -> 파일이 없다면 counter를 설정하지 않으므로 이후
         *      최초 요청에서 CounterUtils가 0부터 시작해 증가
         */
        if (target.exists()) {
            try (FileInputStream fis = new FileInputStream(target);
                 InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {

                long c = Long.parseLong(br.readLine());
                context.setAttribute("counter", c);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("init counter : {}", context.getAttribute("counter"));
    }

    /**
     * 종료(=contextDestroyed)시 동작
     * -> 시작과 동일 방식으로 realPath 계산
     * -> try-with-resource로 BufferWriter 생성
     * -> bw.write(...) :
     *      현재 전역 컨텍스트에 저장된 counter값을 문자열로 파일에 기록
     *      bw.flush()로 디스크 반영
     * -> 종료 시점 로깅 유실 가능성 떄문에 println으로도 출력
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String fileName = context.getInitParameter("counterFileName");
        String filePath = "/WEB-INF/classes/" + fileName;
        String realPath = context.getRealPath(filePath);

        try (
                FileOutputStream fis = new FileOutputStream(realPath,true);
                OutputStreamWriter osw = new OutputStreamWriter(fis, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw)
        ) {
            bw.write(String.valueOf(context.getAttribute("counter")));
//            bw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("distory-counter:" + context.getAttribute("counter"));
    }
}
