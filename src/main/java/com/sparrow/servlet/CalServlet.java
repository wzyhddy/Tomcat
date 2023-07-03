package com.sparrow.servlet;

import com.sparrow.servlet.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CalServlet被调用了");
        String strNum1 = req.getParameter("num1");
        String strNum2 = req.getParameter("num2");
        int num1 = WebUtils.parseInt(strNum1, 0);
        int num2 = WebUtils.parseInt(strNum2, 0);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        int result = num1 + num2;
        writer.print("<h1>" + num1 + "+" + num2 + "=" + result + "</h1>");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
