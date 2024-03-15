package org.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 滑动窗口算法
 * 动态调整窗口的大小，以适应流量的变化
 */
public class SlidingWindow {
    private final long windowSize;//窗口大小，单位毫秒
    private final int maxRequests;//最大请求数
    private List<Long> requests;//窗口内的请求时间列表
    private final Lock requestsLock;//请求锁

    public SlidingWindow(long windowSize, int maxRequests) {
        this.windowSize = windowSize;
        this.maxRequests = maxRequests;
        this.requests = new ArrayList<>();
        this.requestsLock = new ReentrantLock();
    }

    public boolean allowRequest(){
        requestsLock.lock();
        try {
            long currentTime = System.currentTimeMillis();
            while(!requests.isEmpty() && currentTime - requests.get(0) > windowSize){
                requests.remove(0);
            }
            if(requests.size() >= maxRequests){
                return false;
            }
            requests.add(currentTime);
            return true;

        }finally {
            requestsLock.unlock();
        }
    }


    public static void main(String[] args) {
        SlidingWindow limiter = new SlidingWindow(1000, 4);
        for (int i = 0; i < 15; i++) {
            String now = String.format("%tT", System.currentTimeMillis());
            if(limiter.allowRequest()){
                System.out.println(now + " 请求通过");
            }else {
                System.out.println(now + " 请求被限流");
            }
            try {
                Thread.sleep(100); // 模拟请求间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
