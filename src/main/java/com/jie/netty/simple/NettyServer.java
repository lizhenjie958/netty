package com.jie.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws Exception {
        //创建BossGroup 和 WorkerGroup
        //说明
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
        //3. 两个都是无限循环
        //4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
        //   默认实际 cpu核数 * 2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); //8


        //创建服务器端的启动对象，配置参数
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup) // 设置连个线程组
                .channel(NioServerSocketChannel.class)      //使用NioSocketChannel 作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG,128)   // 设置线程队列等待连接个数
            //                    .handler(null) // 该 handler对应 bossGroup , childHandler 对应 workerGroup
                .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道初始化对象(匿名对象)
                    //给pipeline 设置处理器
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //可以使用一个集合管理 SocketChannel， 再推送消息时，可以将业务加入到各个channel 对应的 NIOEventLoop 的 taskQueue 或者 scheduleTaskQueue
                        System.out.println("客户socketchannel hashcode=" + socketChannel.hashCode());
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });  //  // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器


            System.out.println(".....服务器 is ready...");

            //绑定一个端口并且同步生成了一个 ChannelFuture 对象（也就是立马返回这样一个对象）
            //启动服务器(并绑定端口)
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            //给cf 注册监听器，监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("监听端口 6668 成功");
                    }else{
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            //对关闭通道事件  进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}