package org.example.test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * @description: 漏桶算法
 * @author: muqingfeng
 * @date: 2024/3/17 22:26
 */
public class LeakyBucket {
    private final double rate;
    private final int bucketSize;
    private int water;
    private long lastLeakMs;

    public LeakyBucket(double rate, int bucketSize) {
        this.water = 0;
        this.lastLeakMs = System.currentTimeMillis();
        this.rate = rate;
        this.bucketSize = bucketSize;
    }

    public synchronized boolean allow() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastLeakMs;

        int leakedAmount = (int) (elapsed / 1000.0 * rate);
        if (leakedAmount > 0) {
            if (leakedAmount >= water) {
                water = 0;
            } else {
                water -= leakedAmount;
            }
            lastLeakMs = now;
        }

        if (water + 1 > bucketSize) {
            return false;
        } else {
            water++;
            return true;
        }

    }


    public static void main(String[] args) throws InterruptedException {
        LeakyBucket leakyBucket = new LeakyBucket(3, 4);

        // Simulate incoming requests
        for (int i = 1; i <= 15; i++) {
            String currentTime = LocalTime.now().toString();
            if (leakyBucket.allow()) {
                System.out.printf("%s  第 %d 个请求通过\n", currentTime, i);
            } else {
                System.out.printf("%s  第 %d 个请求被限流\n", currentTime, i);
            }
            TimeUnit.MILLISECONDS.sleep(200); // Simulate time interval between requests
        }
    }
}
