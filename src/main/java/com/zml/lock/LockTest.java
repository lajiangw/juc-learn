package com.zml.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-22 13:50
 */
public class LockTest {

    @Test
    public void test() {
//        此时他们持有的是一把锁，不会阻塞线程，直接进入。
        Object o = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("1");
            synchronized (o) {
                System.out.println("2");
                synchronized (o) {
                    System.out.println("3");
                }
            }
        });
        thread.start();
    }

    public synchronized static void m1() {
        System.out.println("m1" + Thread.currentThread().getName());
        new LockTest().m2();
        System.out.println("end m1");
    }

    public synchronized void m2() {
        System.out.println("m2" + Thread.currentThread().getName());
    }


    public static void main(String[] args) {
         Object a = new Object();
         Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                System.out.println("t1线程持有a锁，试图获取b锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println("t1线程获取到b锁");
                }
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println("t2线程持有a锁，试图获取a锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.out.println("t2线程获取到a锁");
                }
            }
        }, "t2").start();
}

    @Test
    public void deadlockDemo() {
    }

}
