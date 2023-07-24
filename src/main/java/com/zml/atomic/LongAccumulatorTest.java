package com.zml.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-24 15:12
 */
public class LongAccumulatorTest {
    public static void main(String[] args) {
//        默认值是 left ，传进来的值是right
        LongAccumulator longAccumulator = new LongAccumulator((left, right) -> left * right, 6);
        longAccumulator.accumulate(2);
        System.out.println(longAccumulator.get());
    }
}
