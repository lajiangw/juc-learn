package com.zml.happensbefore;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-23 14:22
 */
public class VolatileSeeTest {

    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            System.out.println("t1 进入");
            while (flag) {

            }
            System.out.println("程序终止！");
        }).start();

// 即时休眠1秒之后修改flag的值，该值也会影响到另外一个下城，加了volatile关键字的变量是全线程可见的。
        TimeUnit.SECONDS.sleep(1);
        flag = false;
        System.out.println("falg = " + flag);
    }
}
