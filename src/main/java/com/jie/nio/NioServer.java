package com.jie.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception {
        // 创建ServerSockerChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个slector对象
        Selector selector = Selector.open();

        // 绑定端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverScokerChannel 注册到slector    事件为OP_Accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        // 循环等待客户端连接
        while (true){
            if(selector.select(1000) == 0){
                System.out.println("服务器等待了1秒无连接");
                continue;
            }

            // 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                // 如果是OP_ACCEPT 表示为有新的客户端连接
                if(key.isAcceptable()){
                    // 该客户端生成一个 serverSocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞模式
                    socketChannel.configureBlocking(false);

                    System.out.println("客服端连接成功，生成了一个socketChannel" + socketChannel.hashCode());
                    // socketChannel 注册到selector, 关注事件为OP_READ,socketChannel 关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }


                if(key.isReadable()){
                    // 通过key 反向获取到对象的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取到channel对应的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    // 讲通道中的数据读入到buffer中
                    channel.read(buffer);

                    System.out.println("from 客户端" + new String(buffer.array()));
                }

                // 手动删除当前的selectKey 防止重复操作
                selectionKeys.remove(key);
            }
        }
    }
}
