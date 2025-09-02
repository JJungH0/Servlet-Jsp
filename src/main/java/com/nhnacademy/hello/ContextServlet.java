package com.nhnacademy.hello;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/context")
public class ContextServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletContext context = getServletContext();
        context.setAttribute("appName", "MyWebApp");
        context.log("ContextServlet 실행됨 !");

        resp.setContentType("text/plain");
        resp.getWriter().println("appName = " + context.getAttribute("appName"));

    }
}
