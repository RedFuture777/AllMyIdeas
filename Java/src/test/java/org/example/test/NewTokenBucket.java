package org.example.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 令牌桶限流
 * @author: muqingfeng
 * @date: 2024/3/17 22:55
 */
public class NewTokenBucket {
    private final double rate;

    public NewTokenBucket(double rate, double capacity) {
        this.rate = rate;
        this.capacity = capacity;
        this.updateTime = System.nanoTime();
        this.tokens = capacity;
    }

    private final double capacity;
    private long updateTime;
    private volatile double tokens;
    private final Lock reentrantLocks = new ReentrantLock();

    public boolean allow(){
        reentrantLocks.lock();
        try {
            long now = System.nanoTime();
            double elapsed = TimeUnit.NANOSECONDS.toSeconds(now - updateTime);
            double tokenToAdd = elapsed * rate;
            tokens = Math.min(capacity, tokens + tokenToAdd);
            boolean allow = false;
            if(tokens >= 1.0){
                tokens--;
                allow = true;
                updateTime = now;
            }
            return allow;
        }finally {
            reentrantLocks.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        NewTokenBucket tokenBucket = new NewTokenBucket(3.0, 4.0);

        for (int i = 1; i <= 10; i++) {
            if (tokenBucket.allow()) {
                System.out.println(String.format("第 %d 个请求通过", i));
            } else {
                System.out.println(String.format("第 %d 个请求被限流", i));
            }
            Thread.sleep(200);
        }
    }
}
