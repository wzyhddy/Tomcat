package com.sparrow.servlet.utils;

import com.sparrow.servlet.tomcatV3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author 诉衷情の麻雀
 * @Description TODO
 * @date 2023/7/2
 * @Version 1.0
 */
public class WebUtils {
    /**
     * 将一个字符串数字转为int，如果转换失败 返回默认值
     * @param strNum
     * @param defaultVal
     * @return
     */
    public static int parseInt(String strNum, int defaultVal) {
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum + " 格式不对 转换失败");
        }
        return defaultVal;
    }

    //判定uri是不是html文件
    public static boolean isHtml(String uri) {
        return uri.endsWith(".html");
    }

    //根据文件名来读取该文件->String
    public static String readHtml(String filename) {
        String path = tomcatV3.class.getResource("/").getPath();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path + filename));
            String buf = "";
            while ((buf = bufferedReader.readLine()) != null) {
                stringBuilder.append(buf);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
