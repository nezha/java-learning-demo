package com.nezha.learn.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/14 10:04 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class ImConnection {
    private Channel channel;
    public Channel connect(String host, int port) {
        doConnect(host, port);
        return this.channel;
    }
    private void doConnect(String host, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder", new StringDecoder());
                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new ClientStringHandler());
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            channel = f.channel();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        String host = "127.0.0.1";
        int port = 2222;
        Channel channel = new ImConnection().connect(host, port);
//        while (true){
//            channel.writeAndFlush("aaaaa-你好服务器-"+System.currentTimeMillis()).sync();
//            try {
//                Thread.sleep(10000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//
//        }
        channel.writeAndFlush("aaaaa-你好服务器-"+System.currentTimeMillis()).sync();
        channel.closeFuture().sync();

    }
}
