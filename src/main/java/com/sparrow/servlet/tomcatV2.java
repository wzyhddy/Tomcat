package com.sparrow.servlet;

import com.sparrow.servlet.tomcat.handler.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: 诉衷情の麻雀
 * @Description: TODO
 * @DateTime: 2023/7/3 13:12
 **/
public class tomcatV2 {
    public static void main(String[] args) throws IOException {
        //在8080端口监听
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("==========tomcatV2在8080端口监听================");
        //只要serverSocket没有关闭，就一直等待浏览器/客户端连接
        while (!serverSocket.isClosed()) {
            //1.接收到浏览器的连接后，如果成功，就会得到socket
            //2.这个socket就是服务器和浏览器的数据通道
            Socket socket = serverSocket.accept();
            //3.创建一个线程对象，并且把socket给该线程
            new Thread(new RequestHandler(socket)).start();
        }
    }
}
