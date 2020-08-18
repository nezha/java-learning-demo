package com.nezha.learn.demo.zerocopy.zerocopy;

import com.nezha.learn.demo.zerocopy.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TransferToServer
 *
 * @author: chentong
 * Date:     2019/1/13 14:57
 */
public class TransferToServer {
    ServerSocketChannel listener = null;

    protected void mySetup() {
        InetSocketAddress listenAddr = new InetSocketAddress(Constants.SERVER_PORT);

        try {
            listener = ServerSocketChannel.open();
            ServerSocket ss = listener.socket();
            ss.setReuseAddress(true);
            ss.bind(listenAddr);
            System.out.println("Listening on port : " + listenAddr.toString());
        } catch (IOException e) {
            System.out.println("Failed to bind, is port : " + listenAddr.toString()
                    + " already in use ? Error Msg : " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TransferToServer dns = new TransferToServer();
        dns.mySetup();
        dns.readData();
    }

    private void readData() {
        ByteBuffer dst = ByteBuffer.allocate(4096);
        try {
            while (true) {
                SocketChannel conn = listener.accept();
                System.out.println("Accepted : " + conn);
                conn.configureBlocking(true);
                long sum = 0;
                int nread = 0;
                while (nread != -1) {
                    try {
                        nread = conn.read(dst);
                    } catch (IOException e) {
                        e.printStackTrace();
                        nread = -1;
                    }
                    sum += nread;
                    dst.rewind();
                }
                System.out.println("receive total " + sum + " bytes");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

