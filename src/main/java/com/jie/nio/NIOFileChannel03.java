package com.jie.nio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\35017\\Desktop\\hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inChannel = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\35017\\Desktop\\hello2.txt");
        FileChannel outChannel = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());


        while (true){
            // 清空buffer
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            if(read == -1){
                break;
            }

            byteBuffer.flip();
            outChannel.write(byteBuffer);

        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
