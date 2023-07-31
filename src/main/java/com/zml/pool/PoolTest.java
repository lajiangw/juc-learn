package com.zml.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 18:02
 */
public class PoolTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                executorService2.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });
            }
        } finally {
            executorService2.shutdown();
        }

    }
}
