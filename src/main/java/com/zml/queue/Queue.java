package com.zml.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 17:39
 */
public class Queue {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> strings = new ArrayBlockingQueue<String>(2);

        System.out.println(strings.add("1"));
        System.out.println(strings.add("2"));
//        System.out.println(strings.add("3"));
        System.out.println(strings.remove());


    }
}
