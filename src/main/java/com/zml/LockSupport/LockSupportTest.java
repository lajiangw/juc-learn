package com.zml.LockSupport;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-22 17:37
 */
public class LockSupportTest {


    public static void main(String[] args) {
//        此时如果在线程还没有wait的状态下去释放，会导致线程一致堵塞。
        Object o1 = new Object();
        new Thread(() -> {
            synchronized (o1) {
                System.out.println("t1 come in");
                try {
                    System.out.println("t1 stop---");
                    o1.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();

        CompletableFuture.runAsync(() ->
        {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (o1) {
                o1.notify();
                System.out.println("释放t1");
            }
        });
    }

    @Test
    public void conditionTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        创建一个锁的实例。
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        CompletableFuture.runAsync(() -> {
//            获得锁
            lock.lock();
            System.out.println("t1 come in~");
            try {
                System.out.println("堵塞前");
                condition.await();
                System.out.println("堵塞后");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("释放锁");
                lock.unlock();
            }

        }, executorService);

        CompletableFuture.runAsync(() -> {
            lock.lock();
            try {
                System.out.println("t2 come in");
                TimeUnit.SECONDS.sleep(1);
                condition.signal();
                System.out.println("释放t1");
                lock.unlock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, executorService);
        executorService.shutdownNow();
    }
}
