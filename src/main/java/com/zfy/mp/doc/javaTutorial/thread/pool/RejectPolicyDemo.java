package com.zfy.bwcj.javaTutorial.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: RejectPolicyDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool
 * @描述: 线程池拒绝策略演示
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class RejectPolicyDemo {

    static class TargetTask implements Runnable {
        static AtomicInteger taskNo = new AtomicInteger(1);
        private String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo;
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 执行: " + taskName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== 四种拒绝策略对比 ==========\n");

        // 1. AbortPolicy (默认策略) - 抛出异常
        testAbortPolicy();

        Thread.sleep(3000);
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 2. CallerRunsPolicy - 调用者执行（背压）
        testCallerRunsPolicy();

        Thread.sleep(3000);
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 3. DiscardPolicy - 静默丢弃
        testDiscardPolicy();

        Thread.sleep(3000);
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 4. DiscardOldestPolicy - 丢弃最老任务
        testDiscardOldestPolicy();
    }

    /**
     * 1. AbortPolicy - 默认策略，抛出RejectedExecutionException
     */
    private static void testAbortPolicy() {
        System.out.println("【1】AbortPolicy: 抛出异常");
        ThreadPoolExecutor executor = createExecutor(new ThreadPoolExecutor.AbortPolicy());

        for (int i = 1; i <= 6; i++) {
            try {
                executor.submit(new TargetTask());
            } catch (RejectedExecutionException e) {
                System.out.println("  → 任务被拒绝，抛出异常: " + e.getClass().getSimpleName());
            }
        }
        executor.shutdown();
    }

    /**
     * 2. CallerRunsPolicy - 由调用者线程执行
     * 提供背压(backpressure)机制，降低提交速度
     */
    private static void testCallerRunsPolicy() {
        System.out.println("【2】CallerRunsPolicy: 调用者执行（提供背压）");
        ThreadPoolExecutor executor = createExecutor(new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i <= 6; i++) {
            System.out.println("  → main线程提交任务" + i);
            executor.submit(new TargetTask());
        }
        executor.shutdown();
    }

    /**
     * 3. DiscardPolicy - 静默丢弃任务（无任何提示）
     */
    private static void testDiscardPolicy() {
        System.out.println("【3】DiscardPolicy: 静默丢弃任务");
        ThreadPoolExecutor executor = createExecutor(new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("  → 提交6个任务，最后4个会被静默丢弃");
        for (int i = 1; i <= 6; i++) {
            executor.submit(new TargetTask());
        }
        executor.shutdown();
    }

    /**
     * 4. DiscardOldestPolicy - 丢弃队列中最老的任务
     */
    private static void testDiscardOldestPolicy() {
        System.out.println("【4】DiscardOldestPolicy: 丢弃队列中最老的任务");
        ThreadPoolExecutor executor = createExecutor(new ThreadPoolExecutor.DiscardOldestPolicy());

        System.out.println("  → 提交6个任务，队列最老的任务会被丢弃");
        for (int i = 1; i <= 6; i++) {
            executor.submit(new TargetTask());
        }
        executor.shutdown();
    }

    /**
     * 创建一个容易触发拒绝的线程池
     * 核心:1, 最大:1, 队列:1
     */
    private static ThreadPoolExecutor createExecutor(RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(
                1,                              // 核心线程数
                1,                              // 最大线程数
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),    // 队列容量1
                Executors.defaultThreadFactory(),
                handler                         // 自定义拒绝策略
        );
    }
}
