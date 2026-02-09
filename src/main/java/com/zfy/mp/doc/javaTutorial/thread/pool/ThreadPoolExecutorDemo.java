package com.zfy.bwcj.javaTutorial.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: ThreadPoolExecutorDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool
 * @描述: ThreadPoolExecutor 核心示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class ThreadPoolExecutorDemo {

    static class TargetTask implements Runnable {
        static AtomicInteger taskNo = new AtomicInteger(1);
        private String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo;
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 正在执行: " + taskName);
            try {
                Thread.sleep(2000); // 模拟任务执行2秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 完成任务: " + taskName);
        }
    }

    public static void main(String[] args) {
        // 创建ThreadPoolExecutor - 这是理解线程池的关键
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                      // corePoolSize: 核心线程数 - 即使空闲也会存活
                4,                      // maximumPoolSize: 最大线程数 - 核心线程数 + 缓冲线程数
                60L,                    // keepAliveTime: 非核心线程空闲存活时间
                TimeUnit.SECONDS,       // keepAliveTime的时间单位
                new ArrayBlockingQueue<>(3), // workQueue: 任务队列，容量为3
                new ThreadFactory() {
                    private AtomicInteger threadNo = new AtomicInteger(1);
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "pool-thread-" + threadNo.getAndIncrement());
                    }
                },
                new ThreadPoolExecutor.AbortPolicy() // handler: 拒绝策略
        );

        System.out.println("=== 线程池初始状态 ===");
        printPoolStatus(executor);

        System.out.println("\n=== 开始提交任务 (总共8个任务) ===");
        System.out.println("核心线程:2, 队列容量:3, 最大线程:4");
        System.out.println("预期: 2核心线程执行 + 3入队列 + 2创建非核心线程 = 7任务可处理");
        System.out.println("第8个任务会被拒绝\n");

        for (int i = 0; i < 8; i++) {
            try {
                executor.submit(new TargetTask());
                printPoolStatus(executor);
            } catch (RejectedExecutionException e) {
                System.out.println("任务 " + (i + 1) + " 被拒绝! 队列和线程都已满");
            }
        }

        System.out.println("\n=== 等待任务完成 ===");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printPoolStatus(executor);

        System.out.println("\n=== 关闭线程池 ===");
        executor.shutdown();
    }

    /**
     * 打印线程池状态
     */
    private static void printPoolStatus(ThreadPoolExecutor executor) {
        System.out.printf(
                "[池状态] 核心:%d, 当前:%d/%d, 活跃:%d, 队列:%d, 完成:%d%n",
                executor.getCorePoolSize(),      // 核心线程数
                executor.getPoolSize(),          // 当前线程池大小
                executor.getMaximumPoolSize(),   // 最大线程数
                executor.getActiveCount(),       // 活跃线程数
                executor.getQueue().size(),     // 队列中的任务数
                executor.getCompletedTaskCount()  // 已完成任务数
        );
    }
}
