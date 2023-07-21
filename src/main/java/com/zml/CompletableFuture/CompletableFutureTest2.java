package com.zml.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-21 17:11
 */
public class CompletableFutureTest2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("123");
//            int i = 10 /0;
            return 3;
        }, executorService).whenComplete((integer, throwable) -> {
            if (throwable == null) {
                System.out.println("计算结果正常没有错误");
            }
            System.out.println(integer);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            System.out.println("出现了异常");
            return null;
        });
        executorService.shutdownNow();
    }

}
