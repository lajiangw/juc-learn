package com.zml.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-27 15:22
 */
public class StampedLockDemo {
    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    int result = 0;

    public void read() {
        long l = stampedLock.tryOptimisticRead();
        result = number;
//        validate 如果当前读没有其他写线程修改则返回true 反之为 false
        System.out.println("3秒前" + stampedLock.validate(l));

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3秒后" + stampedLock.validate(l));

        if (!stampedLock.validate(l)) {
//            有其他写在读的过程中修改了 ，
            long l1 = stampedLock.readLock();
            System.out.println("升级为悲观读，读的过程中，不允许其他写操作进来");
            result = number;
            System.out.println("result = " + result);
            stampedLock.unlockRead(l1);
        }
        System.out.println(Thread.currentThread().getName() + "线程result = " + result);
    }

    public void write() {
        long l = stampedLock.writeLock();
        System.out.println("写操作准备修改");
        result = number + 13;
        stampedLock.unlockWrite(l);
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程结束修改");
    }

    public static void main(String[] args) throws InterruptedException {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        new Thread(stampedLockDemo::read, "t1").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(stampedLockDemo::write, "t2").start();
        System.out.println("result = "+stampedLockDemo.result);
    }
}
