package com.zml.auxiliary;

import org.junit.Test;

import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 14:59
 */
public class JucAuxiliary {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    out.println(Thread.currentThread().getName() + "抢到了车位");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        out.println(Thread.currentThread().getName() + "离开了车位");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

    @Test
    public void test() {
//        只有当await方法 达到数字屏障之后，才会执行 CyclicBarrier 的方法。
        int number = 7;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            out.println("可以召唤神龙了~");
        });

        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                out.println(Thread.currentThread().getName() + "集齐了龙珠~");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Test
    public void test2() {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            try {
                new Thread(() -> {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    out.println(Thread.currentThread().getName() + "抢到了车位");

                    try {
                        TimeUnit.SECONDS.sleep(2);
                        out.println(Thread.currentThread().getName() + "离开了车位");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } finally {
                semaphore.release();
            }
        }
    }
}
