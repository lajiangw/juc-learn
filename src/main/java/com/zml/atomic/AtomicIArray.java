package com.zml.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-24 12:13
 */
public class AtomicIArray {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3});

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        int andSet = atomicIntegerArray.getAndSet(2, 2022);
        System.out.println(andSet+"  "+atomicIntegerArray.get(0));
    }
}
