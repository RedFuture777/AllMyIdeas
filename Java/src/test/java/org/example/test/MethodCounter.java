package org.example.test;

/**
 * 统计方法的调用次数
 */
public class MethodCounter {
    private static int count = 0;

    public static void incrementCount(){
        count++;
    }

    // 获取方法调用次数
    public static int getCount() {
        return count;
    }

    // 清零计数器
    public static void resetCount() {
        count = 0;
    }
}
