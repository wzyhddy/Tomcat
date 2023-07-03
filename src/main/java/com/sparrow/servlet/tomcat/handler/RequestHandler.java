package com.sparrow.servlet.tomcat.handler;

import com.sparrow.servlet.SparrowCalServlet;
import com.sparrow.servlet.SparrowHttpServlet;
import com.sparrow.servlet.http.SparrowRequest;
import com.sparrow.servlet.http.SparrowResponse;
import com.sparrow.servlet.tomcatV3;
import com.sparrow.servlet.utils.WebUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;

/**
 * @Author: 诉衷情の麻雀
 * @Description: 是一个线程对象
 * 处理一个http请求的
 * @DateTime: 2023/7/3 12:57
 **/
public class RequestHandler implements Runnable {

    //定义Socket
    private Socket socket = null;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        //这里我们可以对客户端/浏览器进行IO编程/交互
        try {

            SparrowRequest sparrowRequest = new SparrowRequest(socket.getInputStream());
            SparrowResponse sparrowResponse = new SparrowResponse(socket.getOutputStream());
            //创建SparrowCalServlet对象

            //1.得到uri->servletName->servlet的实例，真正的运行类型是其子类sparrowCalServlet
            String uri = sparrowRequest.getUri();
            if(WebUtils.isHtml(uri)){ //就是静态页面
                String content = WebUtils.readHtml(uri.substring(1));
                content = SparrowResponse.respHeader + content;
                System.out.println("content=" + content);
                //准备返回 得到outputStream,返回信息给浏览器
                OutputStream outputStream = sparrowResponse.getOutputStream();
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
                socket.close();
                return;
            }
            String servletName = tomcatV3.servletUrlMapping.get(uri);
            if (servletName == null) {
                servletName = "";
            }
            //2.通过uri->servletName->servlet的实例
            SparrowHttpServlet sparrowHttpServlet = tomcatV3.servletMapping.get(servletName);

            //3.调用service方法，通过oop的动态绑定机制，调用运行类型的doGet/doPost
            if (sparrowHttpServlet != null) {
                sparrowHttpServlet.service(sparrowRequest,sparrowResponse);
            }else {
                //没有这个servlet返回404
                String resp = SparrowResponse.respHeader + "<h1>404 Not Found</h1>";
                OutputStream outputStream = sparrowResponse.getOutputStream();
                outputStream.write(resp.getBytes());
                outputStream.flush();
                outputStream.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
