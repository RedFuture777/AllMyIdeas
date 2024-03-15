package org.example.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 固定窗口算法
 * 用于限制一段随时间内某个操作的频率
 */
public class FixedWindowRateLimiter {
    private final int limit;//限流阈值

    private final AtomicInteger counter;//计数器

    private final long windowSize; //时间窗口大小，单位毫秒

    private long windowStart;//窗口起始时间

    public FixedWindowRateLimiter(int limit, long windowSize) {
        this.limit = limit;
        this.windowSize = windowSize;
        this.counter = new AtomicInteger(0);
        this.windowStart = System.currentTimeMillis();
    }


    public boolean tryAcquire() {
        //获取当前时间
        long currentTime = System.currentTimeMillis();
        //如果当前时间超过了窗口的结束时间，则重置计数器和窗口起始时间
        if (currentTime - windowStart >= windowSize) {
            counter.set(0);
            windowStart = currentTime;
        }
        //判断当前计数是否超过阈值
        if (counter.get() < limit) {
            counter.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    public int getCount(){
        return counter.get();
    }
}
