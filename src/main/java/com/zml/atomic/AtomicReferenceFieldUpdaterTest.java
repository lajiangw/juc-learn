package com.zml.atomic;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-24 13:17
 */
public class AtomicReferenceFieldUpdaterTest {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        MyClass myClass = new MyClass();
        for (int i = 0; i < 5; i++) {
            CompletableFuture.runAsync(() -> {
                myClass.init(myClass);
            });
        }
        executorService.shutdownNow();
    }
}

class MyClass {
    public volatile Boolean flag = false;
    AtomicReferenceFieldUpdater<MyClass, Boolean> fieldUpdater
            = AtomicReferenceFieldUpdater.newUpdater(MyClass.class, Boolean.class, "flag");


    public void init(MyClass myClass) {
        if (fieldUpdater.compareAndSet(myClass, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName() + "初始化成功~");
        } else {
            System.out.println("已经被初始化了~");
        }
    }
}
