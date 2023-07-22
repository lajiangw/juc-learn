package com.zml.interrupt;

import org.junit.Test;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-22 15:51
 */
public class VolatileTest {
    static volatile boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                if (flag) {
                    System.out.println("t1 结束，flag 为ture");
                    break;
                }
                System.out.println("t1 come in ");
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t2 come in ");
            flag = true;
        }).start();
    }

    @Test
    public void interruptedTest() {


        /**
         * main	false
         * main	false
         * -----------1
         * -----------2
         * main	true
         * main	false
         */
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());//false
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());//false
        System.out.println("-----------1");
        Thread.currentThread().interrupt();
        System.out.println("-----------2");
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());//true
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());//false
    }
}
