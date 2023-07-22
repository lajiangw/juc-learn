package com.zml.LockSupport;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-22 18:09
 */
public class LockSupportTest2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
            LockSupport.park();
            System.out.println("t2 end");
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            System.out.println("t2");
            LockSupport.unpark(t1);
            System.out.println("释放t1");
        }).start();
    }
}
