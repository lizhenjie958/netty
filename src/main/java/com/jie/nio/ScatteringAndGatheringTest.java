package com.jie.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        // 使用serverSocketChannel 和 SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);


        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        // 循环读取
        while (true){

            int byteRead = 0;
            while (byteRead < messageLength){
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead=" + byteRead);
                // 使用流打印，看当前的pisition和limit
                Arrays.asList(byteBuffers).stream().map(buffer ->
                    "position=" + buffer.position() + ",limit=" + buffer.limit()
                ).forEach(System.out :: println);

            }

            // 将所有的buffer翻转
            Arrays.stream(byteBuffers).forEach(ByteBuffer :: flip);

            // 将数据显示会客户端
            long byteWrite = 0;
            while (byteWrite < messageLength){
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            // 将所有的buffer复位
            Arrays.stream(byteBuffers).forEach(ByteBuffer :: clear);

            System.out.println("byteRead=" + byteRead + ",byteWrite=" + byteWrite );
        }

    }
}
