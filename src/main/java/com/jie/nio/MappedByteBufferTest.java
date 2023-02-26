package com.jie.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("hello.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 直接操作内存的内容
         * 1、读写模式
         * 2、可修改的起始位置
         * 3、可修改多少的字节
         * 实际类型
         * DirectByteBuffer
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte)'H');
        map.put(3,(byte)'9');

        randomAccessFile.close();
        System.out.println("修改成功 ");
    }
}
