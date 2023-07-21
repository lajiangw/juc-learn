package com.zml.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-21 11:23
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Cat cat = new Cat();
        FutureTask<String> stringFutureTask = new FutureTask<>(cat);
        new Thread(stringFutureTask).start();
        System.out.println("main end~");
        System.out.println(stringFutureTask.get());
    }
}

class Cat implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("cat coin in~");
        return "喵喵喵~";
    }
}
