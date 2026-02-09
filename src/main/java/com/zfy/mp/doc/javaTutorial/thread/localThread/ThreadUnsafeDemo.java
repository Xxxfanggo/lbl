package com.zfy.mp.doc.javaTutorial.thread.localThread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 线程不安全Demo - 多线程抢占共享资源（不加锁）
 *
 * 演示多个线程同时修改共享计数器，导致数据不一致的问题
 * 使用CyclicBarrier确保所有线程同时开始执行，最大化并发冲突
 */
public class ThreadUnsafeDemo {

    // 共享资源 - 线程不安全的计数器
    private static int counter = 0;

    // 线程数量
    private static final int THREAD_COUNT = 20;

    // 每个线程执行的次数
    private static final int INCREMENT_COUNT = 1000;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // 用于等待所有线程完成
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        // 用于让所有线程同时开始执行
//        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, () -> {
//            System.out.println("所有线程已就绪，开始并发执行...");
//        });

        System.out.println("开始测试线程不安全的计数器...");
        System.out.println("预期结果: " + (THREAD_COUNT * INCREMENT_COUNT));

        // 创建并启动多个线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    // 等待所有线程就绪
//                    barrier.await();
                    // or 模拟耗时
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 所有线程同时开始执行
                    for (int j = 0; j < INCREMENT_COUNT; j++) {
                        // 直接修改共享变量，没有同步机制
                        counter++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 线程完成，计数减1
                    latch.countDown();
                }
            }, "Thread-" + i).start();
        }

        // 等待所有线程完成
        latch.await();

        // 输出最终结果
        System.out.println("实际结果: " + counter);
        System.out.println("数据差异: " + (THREAD_COUNT * INCREMENT_COUNT - counter));

        // 验证结果
        if (counter != THREAD_COUNT * INCREMENT_COUNT) {
            System.out.println("❌ 线程不安全导致数据错误！");
        } else {
            System.out.println("✅ 结果正确（概率极低）");
        }
    }
}