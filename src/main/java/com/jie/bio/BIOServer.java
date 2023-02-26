package com.jie.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws Exception {
        // 创建serverSocker
        final ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true){
            // 监听，等待客户端连接，阻塞
            System.out.println("等待链接");
            final Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");

            // 创建一个线程，与之通讯
            executorService.execute(new Runnable() {
                public void run() {
                    // 与客户端通讯
                    handler(accept);
                }
            });
        }

    }


    public static void handler(Socket socket){
        try {
            System.out.println("线程信息id=" + Thread.currentThread().getId() + "，名字=" + Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true){
                System.out.println("线程信息id=" + Thread.currentThread().getId() + "，名字=" + Thread.currentThread().getName());
                // reading
                System.out.println("reading");
                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
