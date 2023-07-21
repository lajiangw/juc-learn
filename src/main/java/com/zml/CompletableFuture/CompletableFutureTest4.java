package com.zml.CompletableFuture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ZhangMinlei
 * @description
 * @date 2023-07-21 18:02
 */
public class CompletableFutureTest4 {
    static List<NetAll> list = new ArrayList<>();

    public static void main(String[] args) {
        list.add(new NetAll("jd"));
        list.add(new NetAll("tb"));
        list.add(new NetAll("ddangdang"));
        //        单线程去查询
        List<String> list1 = get(list, "mysql");
        list1.forEach(System.out::println);
        System.out.println("------------------");
        //        多线程去查询
        List<String> list2 = get2(list, "mysql");
        list2.forEach(System.out::println);
    }

    public static List<String> get(List<NetAll> list, String bookName) {
//        单线程去查询
        return list.stream().map(netAll -> {
            return String.format(bookName + "在 %s 售价 %.2f元", netAll.getNetMallName(), netAll.getPrice());
        }).collect(Collectors.toList());
    }

    public static List<String> get2(List<NetAll> list, String bookName) {
        return list.stream().
                map(netAll -> CompletableFuture.supplyAsync(() ->
                        String.format(bookName + "在 %s 售价 %.2f元", netAll.getNetMallName(), netAll.getPrice()))).collect(Collectors.toList()).
                stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class NetAll {

    private String netMallName;

    public double getPrice() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + netMallName.charAt(0);
    }
}
