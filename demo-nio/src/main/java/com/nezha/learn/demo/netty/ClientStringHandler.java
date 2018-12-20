package com.nezha.learn.demo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: xxxxx <br>
 * @Date: 2018/12/14 10:04 AM <br>
 * @Author: objcat <br>
 * @Version: 1.0 <br>
 */
public class ClientStringHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("client:" + msg.toString());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
