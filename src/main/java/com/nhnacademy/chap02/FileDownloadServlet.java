package com.nhnacademy.chap02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
/**
 * 서블릿 등록 애노테이션 (web.xml 대체)
 * name :
 * -> 서블릿 이름 지정 ("fileDownloadServlet")
 * urlPatterns :
 * -> URL 매핑 경로 ("/file/fileDownload")
 *
 */
@WebServlet(
        name = "fileDownloadServlet",
        urlPatterns = "/file/fileDownload"
)
public class FileDownloadServlet extends HttpServlet {
    /**
     * 파일이 저장된 서버 디렉토리 경로를 상수로 정의
     */
    private static final String UPLOAD_FILE = "/Users/chosun-nhn31/Documents/TA/JSP/hello/src/main/upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("filename");
        if (Objects.isNull(filename) || filename.isBlank()) {
            resp.sendError(400, "fileName parameter is needed");
            return;
        }
        /**
         * File.separator : String
         * File.separtorChar : Char
         * -> 운영체제별 파일 경로 구분자
         * windows: \
         * macOs: /
         */
//        String filePath = UPLOAD_FILE + File.separator + filename;
//        String filePath = Path.of(UPLOAD_FILE, filename).toString();
        Path path = Path.of(UPLOAD_FILE, filename);
        String filePath = path.toString();

        if (!Files.exists(path)) {
            log.error("File not found: {}", filename);
            resp.sendError(404, "File Not Found:" + filename);
            return;
        }

        /**
         * resp.setContentType("application/octet-stream") :
         * -> 범용 바이너리 데이터 타입 설정 (모든 파일 다운로드 가능)
         * resp.setHeader("Content-Disposition", "attachment; filename=" + filename) :
         * -> (Content-Disposition) = 브라우저에 파일 다운로드 지시
         * -> (attachment) = 브라우저에서 열지 않고 다운로드하도록 지정
         * -> (filename=) = 다운로드될 파일명 지정 (특수문자/한글 등 인코딩 처리 부재)
         */
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);

        /**
         * 파일 전송 (스트림 처리)
         * Files.newInputStream(path) :
         * -> 경로를 기준으로 파일 입력 스트림 생성 (NIO API)
         * resp.getOutputStream() :
         * -> 클라이언트로 바이너리 데이터 전송 스트림
         * 버퍼 처리 :
         * -> is.read(buffer) = 버퍼 크기만큼 파일 데이터 읽기
         * -> os.write(buffer, 0, n) = 읽은 만큼만 클라이언트에 쓰기
         */
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];

            int n;
            while (-1 != (n = is.read(buffer))) {
                os.write(buffer, 0, n);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
