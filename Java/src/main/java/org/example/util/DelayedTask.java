package org.example.util;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @description: 实现Delayed 的延时对象
 * @author: muqingfeng
 * @date: 2024/3/18 23:21
 */
public class DelayedTask implements Delayed {
    private String name;
    private long startTime;

    public DelayedTask(String name, long delayInMilliseconds) {
        this.name = name;
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;
    }

    @Override
    public int compareTo(Delayed o) {
        if(this.startTime < ((DelayedTask) o).startTime){
            return -1;
        }else if(this.startTime == ((DelayedTask) o).startTime){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = this.startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    // 实现 toString 方法以便打印
    @Override
    public String toString() {
        return "DelayedTask{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                '}';
    }

    public String getName() {
        return name;
    }
}
