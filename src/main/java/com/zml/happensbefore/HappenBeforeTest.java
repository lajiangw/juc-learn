package com.zml.happensbefore;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-23 12:35
 */
public class HappenBeforeTest {
    public volatile int value = 0;


    public static void main(String[] args) throws InterruptedException {
        HappenBeforeTest t = new HappenBeforeTest();
        new Thread(() -> System.out.println(t.getValue())).start();

        new Thread(() -> {
            t.setValue(2);
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ).start();
    }


    public int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}
