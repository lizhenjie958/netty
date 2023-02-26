package com.jie.nio;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String hello = "hello,老李";
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\35017\\Desktop\\hello.txt");
        FileChannel channe = fileOutputStream.getChannel();

        // 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 将str放入buffer
        buffer.put(hello.getBytes());

        // 将缓冲区反转
        buffer.flip();
        // 将缓冲区的内容写入到通道中
        channe.write(buffer);

        fileOutputStream.close();
    }
}
