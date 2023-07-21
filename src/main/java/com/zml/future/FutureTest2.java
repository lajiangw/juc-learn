package com.zml.future;

import java.sql.Time;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-21 12:04
 */
public class FutureTest2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Dog dog = new Dog();
        FutureTask<String> task1 = new FutureTask<>(dog);
        executorService.submit(task1);
        System.out.println("main end");
        if (task1.isDone()) {
            System.out.println(task1.get());
        }
        executorService.shutdownNow();
    }

}

class Dog implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("小狗方法");
        Thread.sleep(3 * 1000);
        return "dog" + this.getClass();
    }
}
