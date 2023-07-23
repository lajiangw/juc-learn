package com.zml.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-23 17:09
 */
public class CasTest {
    public static void main(String[] args) {
//        AtomicInteger构造器传入的参数为初始值，
        AtomicInteger atomicInteger = new AtomicInteger(5);
//        compareAndSet会拿 expect 参数和 初始值比较（5） 如果相等，说明没有修改过，将值修改为10 也就是 update参数的值，
//        如果不相等，不作操作
        atomicInteger.compareAndSet(0, 10);
        System.out.println(atomicInteger.get());
    }

    @Test
    public void atomicReferenceTest() {
        User user1 = new User("张三", 12);
        User user2 = new User("李四", 15);
//        创建一个存放user 的原子容器
        AtomicReference<User> userAtomicReference = new AtomicReference<>();
//        将user1 这个对象设置进去
        userAtomicReference.set(user1);
//        比较默认值是否为user1 ，如果是 就修改为user2，反之没有操作。
        System.out.println(userAtomicReference.compareAndSet(user1, user2) + userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(user1, user2) + userAtomicReference.get().toString());
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private String name;
    private Integer age;
}
