package com.sparrow.servlet;

import com.sparrow.servlet.tomcat.handler.RequestHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 诉衷情の麻雀
 * @Description: 实现通过xml+反射来初始化容器
 * @DateTime: 2023/7/3 16:34
 **/
public class tomcatV3 {

    //1.存放容器servletMapping
    //HashMap
    //key -value
    //ServletName 对应的实例
    public static final ConcurrentHashMap<String, SparrowHttpServlet> servletMapping = new ConcurrentHashMap<String, SparrowHttpServlet>();
    //2.还有一个容器servletURLMapping
    //ConcurrentHashMap
    //HashMap key - value
    //url-Pattern ServletName
    public static final ConcurrentHashMap<String, String> servletUrlMapping = new ConcurrentHashMap<String, String>();

    //直接对两个容器进行初始化
    public void init() {
        //读取web.xml => dom4j
        //得到web.xml文件的路径
        String path = tomcatV3.class.getResource("/").getPath();
        System.out.println("path=" + path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new File(path + "web.xml"));
            System.out.println(document);
            //得到根元素
            Element rootElement = document.getRootElement();
            //得到根元素下面所有的元素
            List<Element> elements = rootElement.elements();
            //遍历并过滤Servlet servletMapping
            for (Element element : elements) {
                if("servlet".equalsIgnoreCase(element.getName())){
                    //这是一个Servlet配置
                    //使用反射将该servlet实例放入到servletMapping
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    servletMapping.put(servletName.getText().trim(),(SparrowHttpServlet) Class.forName(servletClass.getText().trim()).newInstance());
                } else if ("servlet-mapping".equalsIgnoreCase(element.getName())) {
                    //这是一个servlet-mapping配置
                    System.out.println("发现了servlet-mapping的配置");
                    Element servletName = element.element("servlet-name");
                    Element urlPattern = element.element("url-pattern");
                    servletUrlMapping.put(urlPattern.getText(),servletName.getText());
                }
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //验证这两个容器是否初始化
        System.out.println("servletMapping=" + servletMapping);
        System.out.println("servletUrlMapping=" + servletUrlMapping);
    }

    public static void main(String[] args) {
        tomcatV3 tomcatV3 = new tomcatV3();
        tomcatV3.init();
        //启动tomcat容器
        tomcatV3.run();
    }

    //启动tomcatV3容器
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("********tomcatV3在8080端口监听**********8");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
