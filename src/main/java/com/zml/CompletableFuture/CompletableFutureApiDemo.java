package com.zml.CompletableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-21 19:10
 */
public class CompletableFutureApiDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//         thenApply 方法演示 有异常就会终止
//        CompletableFuture.supplyAsync(() -> {
//            System.out.println(111);
//            int i = 9 / 0;
//            return "111";
//        }).thenApply(r -> {
//            System.out.println(222);
//            return r + "222";
//        }).exceptionally(throwable -> {
//            if (throwable != null) {
//                System.out.println("出现了异常");
//                System.out.println(throwable.getMessage());
//            }
//            return null;
//        });

//        handle 演示
        CompletableFuture.supplyAsync(() -> {
            System.out.println("1");
            return 1;
        }, threadPool).handle((i, e) -> {
//            因为这里出现了异常，导致一下代码都不会执行，直接跳到地三个
            int w = 9 / 0;
            System.out.println(2);
            return i + 1;
//        })
//        handle((i, e) -> {
//            System.out.println(3);
//            System.out.println("i = " + i);
//            System.out.println(e.getMessage());
//            return 4;
        }).whenComplete((i, e) -> {
            System.out.println(i);
            if (e == null) {
                System.out.println("没有异常");

            } else {
                System.out.println("出现异常");
            }
        });
        System.out.println("主线程结束");
        threadPool.shutdownNow();
    }

    @Test
    public void thenAccept() {
        CompletableFuture.supplyAsync(() -> "hello").thenAccept(System.out::println);
    }

    @Test
    public void thenATest() {
        CompletableFuture.supplyAsync(() -> "hello").thenAccept(System.out::println);
        CompletableFuture.supplyAsync(() -> "thenRun").thenRun(System.out::println);

        CompletableFuture.supplyAsync(() -> "thenApply").thenApply(f -> {
            System.out.println(f);
            return f;
        });
    }

    @Test
    public void thenAPool() {
//        观察线程池可以看出，当启动xxxAsync方法时，会使用默认的线程池，而不是和上一个方法一致。
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture.runAsync(() -> System.out.println(1 + Thread.currentThread().getName()), executorService)
                .thenRunAsync(() -> System.out.println(1 + Thread.currentThread().getName()))
                .thenRun(() -> System.out.println(1 + Thread.currentThread().getName()));
        System.out.println("main end~");
        executorService.shutdownNow();
    }

    @Test
    public void applyToEitherTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        模拟 a小线程完成需要2秒，b线程完成需要1秒， applyToEither方法使用处理快的返回结果来进行操作
        CompletableFuture<String> planA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("A玩家~");
            return "A";
        }, executorService);

        CompletableFuture<String> planB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("B玩家~");
            return "B";
        }, executorService);

        Thread.sleep(3000);
        CompletableFuture<String> result = planA.applyToEither(planB, f -> f + "获得胜利");

        System.out.println(result.join() + Thread.currentThread().getName());
        executorService.shutdownNow();
    }

    @Test
    public  void thenCombineTest() {
//        链式使用
        CompletableFuture.supplyAsync(() -> {
            System.out.println("1号");
            return 1;
        }).thenApply(x -> {
            System.out.println("2号");
            return 2;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("3号");
            return 3;
        }), (x, y) -> {
            System.out.println("4号");
            System.out.println(x + y);
            return 4;
        });
// 单独使用
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("A");
            return 2;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("A");
            return 2;
        });

        CompletableFuture<Object> result = f1.thenCombine(f2, (x, y) -> {
            System.out.println(x + y);
            return "任务结束~";
        });

        System.out.println(result.join());
    }
}
