package com.zml.thread;

import lombok.Data;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 10:59
 */
public class ThreadDemo {
    public static void main(String[] args) {
        Book book = new Book();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    book.add();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "a").start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    book.remove();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "b").start();
        }
    }
}

@Data
class Book {
    private int number = 0;

    public synchronized void add() throws InterruptedException {

        while (number != 0) {
            this.wait();
        }

        number++;
        System.out.println(Thread.currentThread().getName() + "::" + number);
        this.notifyAll();
    }

    public synchronized void remove() throws InterruptedException {
        while (number != 1) {
            this.wait();
        }

        number--;
        System.out.println(Thread.currentThread().getName() + "::" + number);
        this.notifyAll();
    }
}
