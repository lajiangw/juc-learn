package com.zml.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author ZhangMinlei
 * @description 点赞累计
 * @date 2023-07-24 15:22
 */
public class AccumulatedLikesDemo {
    public static final int _1W = 10000;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        CountDownLatch countDownLatch1 = new CountDownLatch(50);
        CountDownLatch countDownLatch2 = new CountDownLatch(50);
        CountDownLatch countDownLatch3 = new CountDownLatch(50);
        CountDownLatch countDownLatch4 = new CountDownLatch(50);
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10 * _1W; j++) {
                    clickNumber.clickBySynchronized();
                }
                countDownLatch1.countDown();
            }).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickBySynchronized" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10 * _1W; j++) {
                    clickNumber.clickByAtomicLong();
                }
                countDownLatch2.countDown();
            }).start();

        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByAtomicLong" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10 * _1W; j++) {
                    clickNumber.clickByLongAdder();
                }
                countDownLatch3.countDown();
            }).start();

        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByLongAdder" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10 * _1W; j++) {
                    clickNumber.clickByLongAccumulator();
                }
                countDownLatch4.countDown();
            }).start();

        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByLongAccumulator" + (endTime - startTime) + "  " + clickNumber.longAccumulator.get());
    }
}

class ClickNumber {
    int number = 0;

    public synchronized void clickBySynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong();
    LongAdder longAdder = new LongAdder();
    LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    public void clickByLongAdder() {
        longAdder.increment();
    }

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }

    int a = 0;

    public void add(int a) {

    }

    public void setNumber() {
        int b;
        add(b = a);
    }

}
