package com.sparrow.servlet;

import com.sparrow.servlet.http.SparrowRequest;
import com.sparrow.servlet.http.SparrowResponse;
import com.sparrow.servlet.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: 诉衷情の麻雀
 * @Description: TODO
 * @DateTime: 2023/7/3 13:53
 **/
public class SparrowCalServlet extends SparrowHttpServlet{

    public void doGet(SparrowRequest request, SparrowResponse response) {
        System.out.println("SparrowCalServlet被调用了...");
        //写业务代码完成计算任务
        int num1 = WebUtils.parseInt(request.getParameter("num1"), 0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"), 0);
        int sum = num1 + num2;
        //返回结果数据
        OutputStream outputStream = response.getOutputStream();
        String respMes = SparrowResponse.respHeader + "<h1>" + num1 + " + " + num2 + " = " + sum + "tomcatV3-反射+xml创建成功"+ "</h1>";
        try {
            outputStream.write(respMes.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(SparrowRequest request, SparrowResponse response) {
        this.doGet(request, response);
    }

    public void init() throws Exception {

    }

    public void destroy() {

    }
}
