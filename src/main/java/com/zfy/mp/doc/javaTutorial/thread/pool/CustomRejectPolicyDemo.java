package com.zfy.bwcj.javaTutorial.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: CustomRejectPolicyDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool
 * @描述: 自定义拒绝策略示例 - 企业级常用方案
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class CustomRejectPolicyDemo {

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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自定义拒绝策略：记录日志并尝试在调用者线程执行
     */
    static class LoggingAndRunPolicy implements RejectedExecutionHandler {
        private final String poolName;

        public LoggingAndRunPolicy(String poolName) {
            this.poolName = poolName;
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("⚠️ [" + poolName + "] 任务被拒绝!");
            System.out.println("   → 活跃线程: " + executor.getActiveCount());
            System.out.println("   → 队列大小: " + executor.getQueue().size());
            System.out.println("   → 在调用者线程中执行任务作为降级方案");

            if (!executor.isShutdown()) {
                r.run(); // 在当前线程执行
            }
        }
    }

    /**
     * 自定义拒绝策略：将任务放入备份队列（适用于需要保证任务不丢失的场景）
     */
    static class BackupQueuePolicy implements RejectedExecutionHandler {
        private final BlockingQueue<Runnable> backupQueue;
        private final ScheduledExecutorService retryExecutor;

        public BackupQueuePolicy() {
            this.backupQueue = new LinkedBlockingQueue<>();
            // 启动一个重试线程，定期检查主线程池是否有空间
            this.retryExecutor = Executors.newSingleThreadScheduledExecutor();
            startRetryTask();
        }

        private void startRetryTask() {
            retryExecutor.scheduleAtFixedRate(() -> {
                Runnable task = backupQueue.poll();
                if (task != null) {
                    // 尝试重新提交到主线程池（这里简化处理，实际需要获取主线程池引用）
                    System.out.println("🔄 重试执行备份队列中的任务");
                    task.run();
                }
            }, 1, 1, TimeUnit.SECONDS);
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("📦 任务进入备份队列，当前备份任务数: " + backupQueue.size());
            backupQueue.offer(r);
        }

        public void shutdown() {
            retryExecutor.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== 自定义拒绝策略示例 ==========\n");

        // 示例1：日志记录 + 调用者执行（推荐用于电商订单等关键场景）
        testLoggingAndRunPolicy();

        Thread.sleep(3000);
        System.out.println("\n" + "=".repeat(50) + "\n");

        // 示例2：备份队列策略（适用于任务不能丢失的场景）
        testBackupQueuePolicy();
    }

    private static void testLoggingAndRunPolicy() {
        System.out.println("【策略1】日志记录 + 调用者执行");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                new LoggingAndRunPolicy("订单处理池")
        );

        for (int i = 1; i <= 5; i++) {
            executor.submit(new TargetTask());
        }
        executor.shutdown();
    }

    private static void testBackupQueuePolicy() throws InterruptedException {
        System.out.println("【策略2】备份队列（保证任务不丢失）");

        BackupQueuePolicy backupPolicy = new BackupQueuePolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                backupPolicy
        );

        for (int i = 1; i <= 5; i++) {
            executor.submit(new TargetTask());
        }

        Thread.sleep(5000); // 等待备份队列中的任务被重试执行
        executor.shutdown();
        backupPolicy.shutdown();
    }
}
