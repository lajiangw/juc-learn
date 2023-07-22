package com.zml;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-20 20:49
 */
public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("开始运转t1");
        });
//        判断这个线程是否是守护线程
        thread.setDaemon(true);
        System.out.println(thread.getName()+thread.isDaemon());
        thread.start();
        System.out.println("main线程结束~");
    }
}


