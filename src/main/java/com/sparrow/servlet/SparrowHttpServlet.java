package com.sparrow.servlet;

import com.sparrow.servlet.http.SparrowRequest;
import com.sparrow.servlet.http.SparrowResponse;

import java.io.IOException;

/**
 * @Author: 诉衷情の麻雀
 * @Description: TODO
 * @DateTime: 2023/7/3 13:51
 **/
public abstract class SparrowHttpServlet implements SparrowServlet{

    public void service(SparrowRequest request, SparrowResponse response) throws IOException {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            this.doPost(request, response);
        }
    }

    //使用模板设计模式 延迟到子类Servlet实现
    public abstract void doGet(SparrowRequest request, SparrowResponse response);
    public abstract void doPost(SparrowRequest request, SparrowResponse response);
}
