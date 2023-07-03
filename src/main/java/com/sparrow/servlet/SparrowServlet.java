package com.sparrow.servlet;

import com.sparrow.servlet.http.SparrowRequest;
import com.sparrow.servlet.http.SparrowResponse;

import java.io.IOException;

/**
 * @Author: 诉衷情の麻雀
 * @Description: TODO
 * @DateTime: 2023/7/3 13:53
 **/
public interface SparrowServlet {
    void init() throws Exception;

    void service(SparrowRequest request, SparrowResponse response) throws IOException;

    void destroy();
}
