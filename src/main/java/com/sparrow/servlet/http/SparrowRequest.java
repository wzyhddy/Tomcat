package com.sparrow.servlet.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @Author: 诉衷情の麻雀
 * @Description: 封装http请求的数据
 * 比如 method get/post URi/参数
 * @DateTime: 2023/7/3 13:55
 **/
public class SparrowRequest {
    private String method;
    private String uri;

    //存放参数列表 参数名->参数值 HashMap
    private HashMap<String, String> parameterMapping = new HashMap<String, String>();
    private InputStream inputStream = null;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public SparrowRequest(InputStream inputStream) {
        this.inputStream = inputStream;
        //完成对http请求数据的封装...
        init();
    }


    //request对象有个特别重要的方法
    public String getParameter(String name) {
        if (parameterMapping.containsKey(name)) {
            return parameterMapping.get(name);
        }else {
            return "";
        }
    }



    //构造器=> http请求封装 然后提供相关的方法获取
    //inputStream是和对应http请求的socket关联
    public void init() {
        System.out.println("init被调用了........");
        //inputStream->BufferedReader
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            //读取第一行
            /**
             * GET /calServlet?num1=9&num2=8 HTTP/1.1
             */
            String requestLine = bufferedReader.readLine();
            String[] requestLineArr = requestLine.split(" ");
            method = requestLineArr[0];
            //解析得到 /calServlet
            //1.先看看uri有没有参数列表
            int index = requestLineArr[1].indexOf("?");
            if (index == -1) { //说明没有参数列表
                uri = requestLineArr[1];
            }else {
                //[0,index)
                uri = requestLineArr[1].substring(0, index);
                //获取参数列表->parametersMapping
                //parameters num1=9&num2=8
                String parameters = requestLineArr[1].substring(index + 1);

                String[] parametersPair = parameters.split("&");
                //防止用户提交/calServlet?
                if (null != parametersPair && !"".equals(parametersPair)) {
                    //再次分割
                    for (String parameterPair : parametersPair) {
                        //parameterVal ["num1","9"] ["num2","8"]
                        String[] parameterVal = parameterPair.split("=");
                        if (parameterVal.length == 2) {
                            //放入到parametersMapping
                            parameterMapping.put(parameterVal[0], parameterVal[1]);
                        }
                    }
                }
            }
            //这里不能关闭inputStream
//            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String toString() {
        return "SparrowRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", parameterMapping=" + parameterMapping +
                '}';
    }
}
