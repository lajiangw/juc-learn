package com.zml.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-24 13:00
 */
public class AtomicIntegerFieldUpdaterTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Bank bank = new Bank();

        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    bank.addMoney(bank);
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(bank.money);
    }
}

class Bank {
    AtomicIntegerFieldUpdater<Bank> getAndIncrement = AtomicIntegerFieldUpdater.newUpdater(Bank.class, "money");
    public volatile int money = 0;

    public void addMoney(Bank bank) {
        getAndIncrement.getAndIncrement(bank);
    }
}
