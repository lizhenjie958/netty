package com.jie.nio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\35017\\Desktop\\hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inChannel = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\35017\\Desktop\\hello3.txt");
        FileChannel outChannel = fileOutputStream.getChannel();

        outChannel.transferFrom(inChannel,0,file.length());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
