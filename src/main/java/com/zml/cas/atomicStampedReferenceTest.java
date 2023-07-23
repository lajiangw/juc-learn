package com.zml.cas;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-23 21:08
 */
public class atomicStampedReferenceTest {
    public static void main(String[] args) {
        Book javabook = new Book("java", 1);
        Book mysqlbook = new Book("mysql", 1);
//          默认值是java这本书，流水号初始为1
        AtomicStampedReference<Book> reference = new AtomicStampedReference<Book>(javabook, 1);
        System.out.println(reference.getReference() + "\t" + reference.getStamp());
        boolean b;
//        后两个参数表述 预期流水号，和修改后的流水号
        b = reference.compareAndSet(javabook, mysqlbook, reference.getStamp(), reference.getStamp() + 1);
        System.out.println(b + " " + reference.getStamp() + reference.getReference());
        b = reference.compareAndSet(mysqlbook, javabook, reference.getStamp(), reference.getStamp() + 1);
        System.out.println(b + " " + reference.getStamp() + reference.getReference());
    }


    //    多线程使用atomicStampedReference解决ABA问题
    @Test
    public void atomicStampedReferenceT2() throws InterruptedException {
        Book javabook = new Book("java", 1);
        Book mysqlbook = new Book("mysql", 1);
        AtomicStampedReference<Book> reference = new AtomicStampedReference<Book>(javabook, 1);

        new Thread(() -> {
            System.out.println("A线程开始修改~");
            reference.compareAndSet(javabook, mysqlbook, reference.getStamp(), reference.getStamp() + 1);
            reference.compareAndSet(mysqlbook, javabook, reference.getStamp(), reference.getStamp() + 1);
            System.out.println("A线程修改完毕，" + reference.getStamp() + reference.getReference());
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            System.out.println("B线程开始修改");
            boolean b = reference.compareAndSet(mysqlbook, javabook, reference.getStamp(), reference.getStamp() + 1);
            System.out.println("B线程修改结束" + reference.getStamp() + reference.getReference() +b);
        }, "B").start();
    }
}


@Data
@AllArgsConstructor
class Book {
    private String name;
    private int id;
}
