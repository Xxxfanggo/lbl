package com.zfy.bwcj.javaTutorial.thread.localThread;

import java.util.concurrent.CountDownLatch;

/**
 * 线程安全Demo - 多线程抢占共享资源（加锁）
 *
 * 演示使用synchronized关键字保证线程安全，多个线程正确修改共享计数器
 */
public class ThreadSafeDemo {

    // 共享资源 - 线程安全的计数器
    private static int counter = 0;

    // 线程数量
    private static final int THREAD_COUNT = 20;

    // 每个线程执行的次数
    private static final int INCREMENT_COUNT = 1000;

    // 创建同步对象（锁）
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 用于等待所有线程完成
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        System.out.println("开始测试线程安全的计数器...");
        System.out.println("预期结果: " + (THREAD_COUNT * INCREMENT_COUNT));

        // 创建并启动多个线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    // 每个线程执行1000次自增操作
                    for (int j = 0; j < INCREMENT_COUNT; j++) {
                        // 使用synchronized保证同步
                        synchronized (lock) {
                            counter++;
                        }
                    }
                } finally {
                    // 线程完成，计数减1
                    latch.countDown();
                }
            }, "Safe-Thread-" + i).start();
        }

        // 等待所有线程完成
        latch.await();

        // 输出最终结果
        System.out.println("实际结果: " + counter);
        System.out.println("数据差异: " + (THREAD_COUNT * INCREMENT_COUNT - counter));

        // 验证结果
        if (counter == THREAD_COUNT * INCREMENT_COUNT) {
            System.out.println("✅ 线程安全，数据正确！");
        } else {
            System.out.println("❌ 仍然存在线程安全问题（可能是其他原因）");
        }

        // 额外演示：使用AtomicInteger的更优方案
        System.out.println("\n=== 额外演示：使用AtomicInteger ===");
        atomicDemo();
    }

    /**
     * 使用AtomicInteger实现线程安全的计数器
     * 这是最推荐的线程安全计数器实现方式
     */
    private static void atomicDemo() {
        java.util.concurrent.atomic.AtomicInteger atomicCounter
            = new java.util.concurrent.atomic.AtomicInteger(0);

        int THREAD_COUNT = 20;
        int INCREMENT_COUNT = 1000;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        System.out.println("预期结果: " + (THREAD_COUNT * INCREMENT_COUNT));

        // 创建并启动多个线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < INCREMENT_COUNT; j++) {
                        atomicCounter.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            }, "Atomic-Thread-" + i).start();
        }

        // 等待所有线程完成
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("AtomicInteger实际结果: " + atomicCounter.get());

        if (atomicCounter.get() == THREAD_COUNT * INCREMENT_COUNT) {
            System.out.println("✅ AtomicInteger实现完美，无数据竞争！");
        }
    }
}