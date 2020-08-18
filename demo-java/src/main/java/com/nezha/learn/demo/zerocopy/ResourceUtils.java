package com.nezha.learn.demo.zerocopy;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: StreamUtils
 *
 * @author: chentong
 * Date:     2019/1/13 23:24
 */
public class ResourceUtils {

    public static void close(Closeable res) {
        Optional.ofNullable(res).ifPresent(new Consumer<Closeable>() {
            @Override
            public void accept(Closeable closeable) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    System.out.println("close resource failed " + e.getMessage());
                }
            }
        });
    }
}
