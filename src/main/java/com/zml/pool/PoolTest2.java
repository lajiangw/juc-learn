package com.zml.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 18:43
 */
public class PoolTest2 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        threadPoolExecutor.shutdown();
    }
}
