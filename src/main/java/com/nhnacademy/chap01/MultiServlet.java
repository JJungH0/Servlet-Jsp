package com.nhnacademy.chap01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebServlet(value = "/multi",
loadOnStartup = 3)
public class MultiServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        String[] values = req.getParameterValues("class");
        try (PrintWriter pw = new PrintWriter(resp.getWriter())) {
            pw.println(String.join(",", values));
        } catch (IOException e) {
            log.info(e.getMessage());

        }
    }
}
