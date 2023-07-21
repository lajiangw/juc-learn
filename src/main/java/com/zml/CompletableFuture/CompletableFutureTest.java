package com.zml.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-21 13:30
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        启动一个使用默认线程池的异步任务 没有返回值
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> System.out.println("future1"));
        System.out.println(future1.get());

//        启动一个使用自己线程池的异步任务 没有返回值

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> System.out.println("future2"), executorService);
        System.out.println(future2.get());

//        启动一个使用自己线程池，有返回值
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(123);
                return 1;
            }
        }, executorService);
        System.out.println(future3.get());
    }
}
