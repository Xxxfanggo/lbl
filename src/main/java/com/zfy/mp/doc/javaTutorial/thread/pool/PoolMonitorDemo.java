package com.zfy.bwcj.javaTutorial.thread.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: PoolMonitorDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool
 * @描述: 线程池监控示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class PoolMonitorDemo {

    static class TargetTask implements Runnable {
        static AtomicInteger taskNo = new AtomicInteger(1);
        private String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo;
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 开始: " + taskName);
            try {
                // 随机执行时间，模拟不同任务
                Thread.sleep(1000 + (long)(Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 完成: " + taskName);
        }
    }

    /**
     * 线程池监控器 - 定期输出线程池状态
     */
    static class PoolMonitor {
        private final ThreadPoolExecutor executor;
        private final ScheduledExecutorService monitorExecutor;
        private final String poolName;

        public PoolMonitor(ThreadPoolExecutor executor, String poolName) {
            this.executor = executor;
            this.poolName = poolName;
            this.monitorExecutor = Executors.newSingleThreadScheduledExecutor();
            startMonitoring();
        }

        private void startMonitoring() {
            monitorExecutor.scheduleAtFixedRate(() -> {
                printPoolStatus();
            }, 1, 1, TimeUnit.SECONDS);
        }

        private void printPoolStatus() {
            int active = executor.getActiveCount();
            int pool = executor.getPoolSize();
            int queueSize = executor.getQueue().size();
            long completed = executor.getCompletedTaskCount();
            long total = executor.getTaskCount();

            // 计算队列使用率
            int queueCapacity = -1;
            if (executor.getQueue() instanceof ArrayBlockingQueue) {
                queueCapacity = ((ArrayBlockingQueue<?>) executor.getQueue()).remainingCapacity() + queueSize;
            }

            double queueUsage = queueCapacity > 0 ? (queueSize * 100.0 / queueCapacity) : 0;
            double activeRate = pool > 0 ? (active * 100.0 / pool) : 0;

            // 状态条可视化
            String activeBar = "█".repeat(active) + "░".repeat(pool - active);
            String queueBar = "█".repeat(Math.min(queueSize, 20)) + "░".repeat(Math.max(0, 20 - queueSize));

            System.out.printf(
                    "【%s监控】活跃:%d/%d [%s] 队列:%d%s [%s] 完成:%d 总计:%d%n",
                    poolName, active, pool, activeBar, queueSize,
                    queueCapacity > 0 ? String.format("/%d(%.1f%%)", queueCapacity, queueUsage) : "",
                    queueBar, completed, total
            );
        }

        public void shutdown() {
            monitorExecutor.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== 线程池监控示例 ==========\n");

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                          // 核心线程数
                5,                          // 最大线程数
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), // 队列容量
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 启动监控
        PoolMonitor monitor = new PoolMonitor(executor, "业务处理池");

        System.out.println("开始提交20个任务...\n");

        // 批量提交任务
        for (int i = 0; i < 20; i++) {
            executor.submit(new TargetTask());
            Thread.sleep(200); // 每200ms提交一个任务
        }

        // 等待所有任务完成
        while (executor.getActiveCount() > 0 || executor.getQueue().size() > 0) {
            Thread.sleep(500);
        }

        System.out.println("\n所有任务完成，关闭线程池...");
        monitor.shutdown();
        executor.shutdown();
    }

    /**
     * 线程池健康检查（可用于告警）
     */
    static class PoolHealthChecker {
        public enum HealthStatus {
            HEALTHY,    // 健康
            WARNING,    // 警告
            CRITICAL    // 严重
        }

        public static HealthStatus checkHealth(ThreadPoolExecutor executor) {
            int activeCount = executor.getActiveCount();
            int poolSize = executor.getPoolSize();
            int queueSize = executor.getQueue().size();
            int queueCapacity = executor.getQueue().size() + executor.getQueue().remainingCapacity();

            double activeRate = poolSize > 0 ? (double) activeCount / poolSize : 0;
            double queueUsage = queueCapacity > 0 ? (double) queueSize / queueCapacity : 0;

            // 队列超过80%使用率，视为严重
            if (queueUsage > 0.8) {
                return HealthStatus.CRITICAL;
            }
            // 活跃线程超过80% 或 队列超过50%使用率，视为警告
            if (activeRate > 0.8 || queueUsage > 0.5) {
                return HealthStatus.WARNING;
            }
            return HealthStatus.HEALTHY;
        }

        public static void printHealth(ThreadPoolExecutor executor) {
            HealthStatus status = checkHealth(executor);
            String statusEmoji = switch (status) {
                case HEALTHY -> "✅";
                case WARNING -> "⚠️";
                case CRITICAL -> "🚨";
            };
            System.out.println(statusEmoji + " 线程池健康状态: " + status);
        }
    }
}
