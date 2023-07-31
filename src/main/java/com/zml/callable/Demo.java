package com.zml.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 14:23
 */
public class Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        FutureTask 需要Thread 启动。
        FutureTask<String> futureTask = new FutureTask<>(() -> "hello");
        new Thread(futureTask).start();
//        通过使用Get方法获的返回值
        System.out.println(futureTask.get());
    }
}
