package org.example.util;

import java.util.concurrent.DelayQueue;

/**
 * @description: 延时队列例子
 * @author: muqingfeng
 * @date: 2024/3/18 23:30
 */
public class DelayQueueExample {
    private static final int DELAY = 5000; //5s 延迟

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        //添加延时任务
        delayQueue.put(new DelayedTask("Task3",DELAY*3));
        delayQueue.put(new DelayedTask("Task2",DELAY*2));
        delayQueue.put(new DelayedTask("Task1",DELAY));

        while(!delayQueue.isEmpty()){
            DelayedTask delayedTask = delayQueue.take();
            System.out.println(System.currentTimeMillis() + ":取出：" + delayedTask.getName());
        }
    }
}
