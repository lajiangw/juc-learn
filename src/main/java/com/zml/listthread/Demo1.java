package com.zml.listthread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ZhangMinlei
 * @description 三种方式演示
 * @date 2023-07-31 12:49
 */
public class Demo1 {
    public static void main(String[] args) {
//        ArrayList<String> list = new ArrayList<String>();

//        List list = new Vector();
//        List list = Collections.synchronizedList(new ArrayList<>());
        List list = new CopyOnWriteArrayList();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }).start();
        }
    }
}
