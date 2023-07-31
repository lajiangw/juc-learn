package com.zml.listthread;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-31 13:18
 */
public class HashDemo2 {
    public static void main(String[] args) {
//        HashSet set = new HashSet();
        CopyOnWriteArraySet<Object> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }).start();
        }
    }

}
