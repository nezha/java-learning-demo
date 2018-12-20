package com.nezha.learn.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/14 9:58 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 * 通过ServerBootstrap 进行服务的配置，和socket的参数可以通过ServerBootstrap进行设置。
 * 通过group方法关联了两个线程组，NioEventLoopGroup是用来处理I/O操作的线程池，第一个称为“boss”，用来accept客户端连接，第二个称为“worker”，处理客户端数据的读写操作。当然你也可以只用一个NioEventLoopGroup同时来处理连接和读写，bootstrap.group()方法支持一个参数。
 * channel指定NIO方式
 * childHandler用来配置具体的数据处理方式 ，可以指定编解码器，处理数据的Handler
 * 绑定端口启动服务
 */
public class ImServer {

    public static void main(String[] args) {
        int port = 2222;
        new Thread(() -> {
            new ImServer().run(port);
        }).start();
    }
    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast("encoder", new StringEncoder());
                        ch.pipeline().addLast(new ServerStringHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
