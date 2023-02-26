package com.jie.nio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\35017\\Desktop\\hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate((int) file.length());

        // 从通读中读入缓存区
        channel.read(allocate);
        System.out.println(new String(allocate.array()));

        fileInputStream.close();
    }
}
