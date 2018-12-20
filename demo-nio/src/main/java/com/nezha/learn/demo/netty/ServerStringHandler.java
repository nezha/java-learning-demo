package com.nezha.learn.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/14 10:00 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class ServerStringHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println("server:" + msg.toString());
        ctx.writeAndFlush(msg.toString() + ",Hello 客户端");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
