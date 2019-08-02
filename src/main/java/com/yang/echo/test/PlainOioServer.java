package com.yang.echo.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author JiaoYang
 * @date 2019/7/31
 */
public class PlainOioServer {

    public void serve(int port) throws IOException {
        // 绑定服务器到指定的端口。
        final ServerSocket socket = new ServerSocket(port);

        try {
            for (; ; ) {
                // 接受一个连接。
                final Socket clientSocket = socket.accept();    //2
                System.out.println("Accepted connection from " + clientSocket);

                // 创建一个新的线程来处理连接。
                new Thread(new Runnable() {                        //3
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            // 将消息发送到连接的客户端。
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));                            //4
                            out.flush();
                            // 一旦消息被写入和刷新时就 关闭连接。
                            clientSocket.close();                //5

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                            }
                        }
                    }
                    // 启动线程。
                }).start();                                        //6
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
