package com.zml.thread;

import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 12:12
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        Book2 book = new Book2();

        new Thread(() -> {
            try {
                book.add();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, "a").start();


        new Thread(() -> {
            try {
                book.remove();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "b").start();
    }
}


@Data
class Book2 {
    private int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    public void add() throws InterruptedException {

        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + number);
                condition1.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void remove() throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + number);
                condition2.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
