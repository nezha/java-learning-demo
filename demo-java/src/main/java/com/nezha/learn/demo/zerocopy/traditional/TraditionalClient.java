package com.nezha.learn.demo.zerocopy.traditional;

import com.nezha.learn.demo.zerocopy.Constants;
import com.nezha.learn.demo.zerocopy.ResourceUtils;
import org.apache.commons.io.FileUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: TraditionalClient
 *
 * @author: chentong
 * Date:     2019/1/13 11:24
 */
public class TraditionalClient {

    public static void main(String[] args) {
        Socket socket = null;

        DataOutputStream output = null;
        FileInputStream inputStream = null;

        // connect to server
        try {
            socket = new Socket(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSendBufferSize(4096);
            System.out.println("Connected with server " +
                    socket.getInetAddress() +
                    ":" + socket.getPort());
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            String fileName = "/Users/nezha/Downloads/QQ_6.6.0.dmg";
            File file = new File(fileName);
            System.out.println("file size is " + FileUtils.sizeOf(file));
            inputStream = new FileInputStream(fileName);

            output = new DataOutputStream(socket.getOutputStream());
            long start = System.currentTimeMillis();
            byte[] b = new byte[Constants.WRITE_BUFFER_SIZE];
            long read = 0, total = 0;
            while ((read = inputStream.read(b)) >= 0) {
                total = total + read;
                output.write(b);
            }
            output.flush();
            System.out.println("bytes send--" + total + " and totaltime--" + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            ResourceUtils.close(inputStream);
            ResourceUtils.close(output);
            ResourceUtils.close(socket);
        }
    }
}

