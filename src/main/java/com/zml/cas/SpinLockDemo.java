package com.zml.cas;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-23 18:34
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
//        获取当前线程
        Thread thread = Thread.currentThread();
        System.out.println("start");
//        我们要上锁，希望当前没有任何值，所以预期值为空，修改值为现在的线程对象
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unlock() {
//        获取当前线程对象
        Thread thread = Thread.currentThread();
        System.out.println("over~");
//        我们要解锁，当前的预期值必须是我们自己的线程对象，才能解锁，保证原子性，最后将锁置空
        atomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) {
        SpinLockDemo lock = new SpinLockDemo();
        new Thread(() -> {
//            模拟占用锁5s
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();
        }, "A").start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            lock.lock();
            lock.unlock();
        }, "B").start();
    }

    @Test
    public void atomicStampedReferenceTest(){

    }
}
