package com.nezha.learn.demo.zerocopy.traditional;

import com.nezha.learn.demo.zerocopy.Constants;
import com.nezha.learn.demo.zerocopy.ResourceUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TraditionalServer
 *
 * @author: chentong
 * Date:     2019/1/13 11:10
 */
public class TraditionalServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        DataInputStream inputStream = null;

        try {
            serverSocket = new ServerSocket(Constants.SERVER_PORT);
            System.out.println(String.format("Server listening on port %d waiting for client on port", serverSocket.getLocalPort()));

            // server infinite loop for receive data from client
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                inputStream = new DataInputStream(socket.getInputStream());

                byte[] readBuf = new byte[Constants.READ_BUFFER_SIZE];
                while (true) {
                    int readN = inputStream.read(readBuf, 0, readBuf.length);

                    if (-1 == readN) {

                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("io exception " + e.getMessage());
        } finally {
            ResourceUtils.close(serverSocket);
            ResourceUtils.close(inputStream);
        }

    }
}
