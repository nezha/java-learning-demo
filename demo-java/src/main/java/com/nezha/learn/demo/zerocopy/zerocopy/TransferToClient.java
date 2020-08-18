package com.nezha.learn.demo.zerocopy.zerocopy;

import com.nezha.learn.demo.zerocopy.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TransferToClient
 *
 * @author: chentong
 * Date:     2019/1/13 14:59
 */
public class TransferToClient {

    public static void main(String[] args) throws IOException {
        TransferToClient sfc = new TransferToClient();
        sfc.testSendfile();
    }

    public void testSendfile() throws IOException {
        SocketAddress sad = new InetSocketAddress(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
        SocketChannel sc = SocketChannel.open();
        sc.connect(sad);
        sc.configureBlocking(true);

        String fname = "/Users/nezha/Downloads/QQ_6.6.0.dmg";
        long fsize = 54356433L;
        long sendzise = 4094L;

        // FileProposerExample.stuffFile(fname, fsize);
        FileChannel fc = new FileInputStream(fname).getChannel();
        long start = System.currentTimeMillis();
        long nsent = 0, curnset = 0;
        curnset = fc.transferTo(0, fsize, sc);
        System.out.println("total bytes transferred--" + curnset + " and time taken in MS--" + (System.currentTimeMillis() - start));
        //fc.close();
    }


}

