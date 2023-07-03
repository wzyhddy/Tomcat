package com.sparrow.servlet.http;

import java.io.OutputStream;

/**
 * @Author: 诉衷情の麻雀
 * @Description: Response对象可以封装OutputStream(是socket关联)
 * Response对象的作用等价于原生的servlet的HttpServletResponse
 * @DateTime: 2023/7/3 13:58
 **/
public class SparrowResponse {
    private OutputStream outputStream = null;

    //http的响应头
    public static final String respHeader ="HTTP/1.1 200\r\n" +
            "Content-Type: text/html;charset=utf-8\r\n\r\n";

    //OutputStream是和对应http请求的socket关联
    public SparrowResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    //当我们需要给浏览器返回数据时，可以通过SparrowResponse的输出流完成
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
