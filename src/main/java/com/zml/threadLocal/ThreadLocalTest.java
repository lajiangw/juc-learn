package com.zml.threadLocal;

import lombok.Data;

import java.lang.ref.PhantomReference;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-25 11:55
 */
public class ThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Random random = new Random();
        House house = new House();
        for (int i = 0; i < 5; i++) {
            int size = random.nextInt(5) + 1;
            try {
                new Thread(() -> {
                    for (int j = 0; j < size; j++) {
                        house.addNums();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖出" + house.threadLocal.get());
                }).start();
            } finally {
                house.threadLocal.remove();
            }
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(house.threadLocal);
    }
}

@Data
class House {
    private Integer nums;

    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void addNums() {
        threadLocal.set(1 + threadLocal.get());
    }
}
