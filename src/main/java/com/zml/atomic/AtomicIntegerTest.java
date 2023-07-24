package com.zml.atomic;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-24 11:53
 */
public class AtomicIntegerTest {
    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {
//        这里我们使用CountDownLatch来解决。
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        MyNumber myNumber = new MyNumber();

        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.getNumber();
                }
                countDownLatch.countDown();
            }).start();
        }
//        countDownLatch.await()会等待计数器的值为0才会运行，否则会堵塞进程，
        countDownLatch.await();
//        此时我们直接获取值不是我们最终计算的结果，因为其他线程还没有算完main线程就把值取出来了。我们需要使用CountDownLatch
        System.out.println(myNumber.at.get());
    }
}

class MyNumber {
    public AtomicInteger at = new AtomicInteger();

    public void getNumber() {
        at.getAndIncrement();
    }
}
