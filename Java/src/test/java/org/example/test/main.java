package org.example.test;

public class main {
    public static void main(String[] args) {
        //设置限制请求数为10 ，单位为1s
        FixedWindowRateLimiter limiter = new FixedWindowRateLimiter(10, 1000);
        for (int i = 0; i < 1000; i++) {
            if(limiter.tryAcquire()){
                System.out.println("Allow request " + (i + 1) + ", current count: " + limiter.getCount());
            }else {
                System.out.println("Reject request " + (i + 1) + ", current count: " + limiter.getCount());
            }
//            try {
//                Thread.sleep(80);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
