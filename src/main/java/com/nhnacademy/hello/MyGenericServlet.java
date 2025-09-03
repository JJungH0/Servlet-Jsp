package com.nhnacademy.hello;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet(value = "/generic")
public class MyGenericServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello from GenericServlet!");
        resp.getWriter().println("반갑다 제네릭 추상클래스!");

    }
}
