package org.example.util;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureUtil {
    public static void main(String[] args) {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

//        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
//            SmallTool.printTimeAndThread("厨师做菜");
//            SmallTool.sleepMillis(200);
//            return "番茄炒蛋";
//        }).thenCombine(CompletableFuture.supplyAsync(() -> {
//            SmallTool.printTimeAndThread("服务员蒸饭");
//            SmallTool.sleepMillis(300);
//            return "米饭";
//        }), (dish, rich) -> {
//            SmallTool.printTimeAndThread("服务员打饭");
//            SmallTool.sleepMillis(100);
//            return String.format("%s + %s 好了", dish, rich);
//        });


//        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
//            SmallTool.printTimeAndThread("厨师做菜");
//            SmallTool.sleepMillis(200);
//            return "番茄炒蛋";
//        }).thenComposeAsync(dish -> CompletableFuture.supplyAsync(() -> {
//            SmallTool.printTimeAndThread("初始发hi噶");
//
//            SmallTool.printTimeAndThread("厨师打饭");
//            SmallTool.sleepMillis(100);
//            return dish + "+ 米饭";
//        }));


        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("厨师做菜");
            SmallTool.sleepMillis(200);
            return "番茄炒蛋";
        }).thenCompose(dish -> {
            SmallTool.printTimeAndThread("服务员---大大大大");

            return CompletableFuture.supplyAsync(() -> {
                SmallTool.printTimeAndThread("厨师打饭");
                SmallTool.sleepMillis(100);
                return dish + "+ 米饭";
            });
        });

        SmallTool.printTimeAndThread("小明打游戏");
        SmallTool.printTimeAndThread(String.format("%s , 小白开吃", task1.join()));

    }
}
