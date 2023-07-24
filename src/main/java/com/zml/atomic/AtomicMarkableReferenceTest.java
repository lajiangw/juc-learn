package com.zml.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-24 12:36
 */
public class AtomicMarkableReferenceTest {
    static boolean flag = false;

    public   static void main(String[] args) throws InterruptedException {

        AtomicMarkableReference<Integer> marketableReference = new AtomicMarkableReference<>(1, flag);

        new Thread(() -> {
            boolean b = marketableReference.compareAndSet(1, 200, flag, !flag);
            System.out.println("a修改~" + b);
        }).start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            boolean b = marketableReference.compareAndSet(1, 300, flag, !flag);
            System.out.println("b修改" + b);
        }).start();

        System.out.println("当前值" + marketableReference.getReference());
    }
}
