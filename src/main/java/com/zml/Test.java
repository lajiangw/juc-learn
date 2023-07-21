package com.zml;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-20 20:49
 */
public class Test {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> System.out.println("123"));
        thread.start();
    }
}

class Cat implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("我是小猫~");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
