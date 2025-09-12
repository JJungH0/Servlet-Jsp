package com.nhnacademy.chap02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * Jakarta Servlet(구 Servlet 3.0+)에서 파일 업로드를 처리할 떄 사용하는 애노테이션
 * -> 클라이언트가 enctype="multipart/form-data"로 전송한 요청(=파일 업로드 포함)을
 *      서블릿에서 처리할 수 있도록 활성화
 * 적용위치 :
 * -> HttpServlet을 상속한 서블릿 클래스에 선언
 * 효과 :
 * -> HttpServletRequest.getParts() / getPart(String name) 사용 가능
 *      각 업로드 항목은 Part로 다뤄짐
 */
@Slf4j
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB: 이하면 메모리, 초과하면 임시 디스크
        maxFileSize = 1024 * 1024 * 10, // 10MB: 단일 파일 제한
        maxRequestSize = 1024 * 1024 * 100, // 100MB: 요청 전체 제한(=모든파일 합계)
        location = "/Users/chosun-nhn31/Documents/TA/JSP/hello/src/main/upload/temp"

)
public class FileUploadServlet extends HttpServlet {
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String UPLOAD_DIR = "/Users/chosun-nhn31/Documents/TA/JSP/hello/src/main/upload";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /**
         * 업로드 본문을 파싱한 Part들을 모두 순회 (파일 경로 + 일반 필드 파트 포함)
         */
        for (Part part : req.getParts()) {
            /**
             * 각 파트의 Content-Disposition 헤더 취득
             * -> form-data; name="file1"; filename="1.pdf")
             * -> form-data; name='file2"; filename="2.pdf")
             */
            String contentDisposition = part.getHeader(CONTENT_DISPOSITION);

            /**
             * 파일 파트 판단 :
             * -> filename= 이 포함되어 있으면 파일로 간주
             */
            if (contentDisposition.contains("filename=")) {
                /**
                 * 파일명 추출 (헤더 직접 파싱;)
                 */
                String fileName = extractFileName(contentDisposition);

                /**
                 * 실제 업로드된 경우에만 저장 (빈 파일 / 선택 안 함 방지)
                 */
                if (part.getSize() > 0) {
                    /**
                     * 절대 경로로 저장 :
                     * -> @MultipartConfig.location 설정은 무시되고
                     * -> 이 경로에 바로 씀
                     */
                    part.write(UPLOAD_DIR + File.separator + fileName);
                    /**
                     * 임시 저장소 (=메모리 비움) 정리
                     */
                    part.delete();
                }
            } else {
                /**
                 * 일반 폼 필드의 경우 :
                 * -> 파라미터 이름으로 값 조회 (인코딩은 사전 Filter에서 설정 됨)
                 */
                String formValue = req.getParameter(part.getName());
                log.info("{} = {}", part.getName(), formValue);
            }
        }
    }

    /**
     * FileName 추출 메서드
     */
    private String extractFileName(String contentDisposition) {
        /**
         * contentDisposition:form-data; name="file2"; filename="2.pdf"
         */
        log.info("contentDisposition:{}", contentDisposition);
        for (String s : contentDisposition.split(";")) {
            log.info("s -> {}",s);
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

}
