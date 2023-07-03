import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: 诉衷情の麻雀
 * @Description: 第一个版本的tomcat, 可以完成接收浏览器的请求，并返回信息
 * @DateTime: 2023/7/3 11:59
 **/
public class tomcat {
    public static void main(String[] args) throws IOException {
        //1.创建ServerSocket, 在8080端口监听
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("=====mytomcat在8080端口监听=====");
        while (!serverSocket.isClosed()) {
            //等待浏览器/客户端的连接
            //如果有连接就创建一个socket
            Socket socket = serverSocket.accept();
            //先接收浏览器发送的数据
            // 字节流 => BufferedReader(字符流)
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

            String mes = null;
            System.out.println("=====接收到浏览器发送的数据==========");
            //循环的读取
            while ((mes = bufferedReader.readLine()) != null) {
                //判定mes的长度是否为0
                if (mes.length() == 0) {
                    break; //退出循环
                }
                System.out.println(mes);

            }

            //我们的tomcat回送http响应方式
            OutputStream outputStream = socket.getOutputStream();
            //构建一个http响应体
            String respHeader = "HTTP/1.1 200\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n\r\n"; //根据HTTP响应头规则 这里要换一行
            String resp = respHeader + "hi,诉衷情的麻雀";
            System.out.println("***********自定义的tomcat返回的数据如下:****************");
            System.out.println(resp);
            outputStream.write(resp.getBytes()); //将resp字符串以byte[]方式返回

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        }
    }
}
