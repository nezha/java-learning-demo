package com.nezha.learn.demo.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: nezha <br>
 * @Title: ThreadPoolTest <br>
 * @ProjectName: demo <br>
 * @Description: xxxxx <br>
 * @Date: 2020/8/18 10:19 PM <br>
 */
public class ThreadPoolTest {
    /**
     * 设置一个静态的线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE =
            new ThreadPoolExecutor(4, 8, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(2), new ThreadPoolExecutor.AbortPolicy());

    public void runTest() {
        List<Future<String>> tasks = new ArrayList<>();
        tasks.add(EXECUTOR_SERVICE.submit(new CallableTest()));
        tasks.add(EXECUTOR_SERVICE.submit(new CallableTest()));
        tasks.add(EXECUTOR_SERVICE.submit(new CallableTest()));
        tasks.add(EXECUTOR_SERVICE.submit(new CallableTest()));
        tasks.add(EXECUTOR_SERVICE.submit(new CallableTest()));
        tasks.add(EXECUTOR_SERVICE.submit(new CallableTest()));
        tasks.parallelStream().forEach(t -> {
            try {
                String future = t.get(2, TimeUnit.MILLISECONDS);
                System.out.println("get:" + future);
            } catch (Exception e) {

            }
        });
        System.out.println("End");
        EXECUTOR_SERVICE.shutdownNow();
    }

    /**
     * 测试所有任务最长等待时间，然后直接返回现有数据
     * 5秒的整体等待
     */
    public void runTimeOutTest() {
        List<Future<String>> tasks = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        tasks.add(EXECUTOR_SERVICE.submit(() -> getAsynString(countDownLatch)));
        tasks.add(EXECUTOR_SERVICE.submit(() -> getAsynString(countDownLatch)));
        tasks.add(EXECUTOR_SERVICE.submit(() -> getAsynString(countDownLatch)));
        EXECUTOR_SERVICE.submit(() -> timeOutListener(countDownLatch, tasks));
        EXECUTOR_SERVICE.shutdown();

    }

    /**
     * 测试只需要一个任务完成就返回的情况
     */
    public void runJustOneUPTest() {
        List<Future<String>> tasks = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        tasks.add(EXECUTOR_SERVICE.submit(() -> getAsynString(countDownLatch)));
        tasks.add(EXECUTOR_SERVICE.submit(() -> getAsynString(countDownLatch)));
        tasks.add(EXECUTOR_SERVICE.submit(() -> getAsynString(countDownLatch)));
        EXECUTOR_SERVICE.submit(() -> timeOutListener(countDownLatch, tasks));
        EXECUTOR_SERVICE.shutdown();
    }

    private String getAsynString(CountDownLatch countDownLatch) throws InterruptedException {
        String res = "Current Thread is:" + Thread.currentThread().getName() + ",Random number is:" + ThreadLocalRandom.current().nextInt() + "--" + System.currentTimeMillis();
        System.out.println(res);
        int sleepTime = ThreadLocalRandom.current().nextInt(1, 6);
        System.out.println("sleep time is:" + sleepTime);
        Thread.sleep(sleepTime * 1000);
        countDownLatch.countDown();
        return res;
    }

    private void timeOutListener(CountDownLatch countDownLatch, List<Future<String>> tasks) {
        try {
            countDownLatch.await(5, TimeUnit.SECONDS);
            System.out.println("过关了:" + System.currentTimeMillis());
            tasks.parallelStream().forEach(t -> {
                try {
                    if (t.isDone()) {
                        System.out.println("成功完成：" + t.get() + "--" + System.currentTimeMillis());
                    } else {
                        System.out.println("没有完成" + t.cancel(true) + "--" + System.currentTimeMillis());
                    }
                } catch (Exception e) {

                }
            });
        } catch (Exception e) {
            System.out.println("error");
        }

    }

}
