package com.zfy.mp.doc.javaTutorial.collections.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: ConcurrentCollectionsDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.collections.concurrent
 * @描述: JUC 并发集合示例（企业级开发重点）
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class ConcurrentCollectionsDemo {

    public static void main(String[] args) throws InterruptedException {
        concurrentHashMapDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        copyOnWriteArrayListDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        copyOnWriteArraySetDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        concurrentLinkedQueueDemo();
        System.out.println("\n" + "=".repeat(50) + "\n");
        arrayBlockingQueueDemo();
    }

    /**
     * ConcurrentHashMap - 高并发 Map
     * 特点：分段锁/无锁实现，读写不阻塞
     * 适用：高并发场景下替代 HashMap
     */
    private static void concurrentHashMapDemo() throws InterruptedException {
        System.out.println("【ConcurrentHashMap 示例】");

        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

        // 初始化
        map.put("Apple", new AtomicInteger(0));
        map.put("Banana", new AtomicInteger(0));
        map.put("Cherry", new AtomicInteger(0));

        // 多线程并发更新
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        long start = System.nanoTime();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.forEach((k, v) -> v.incrementAndGet());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        long duration = (System.nanoTime() - start) / 1_000_000;

        System.out.println("并发更新结果:");
        map.forEach((k, v) -> System.out.printf("  %s: %d%n", k, v.get()));
        System.out.println("耗时: " + duration + " ms");

        // 原子操作
        map.computeIfAbsent("Grape", k -> new AtomicInteger(0));
        map.computeIfPresent("Apple", (k, v) -> {
            v.addAndGet(100);
            return v;
        });
        System.out.println("\n原子操作后:");
        map.forEach((k, v) -> System.out.printf("  %s: %d%n", k, v.get()));

        // 规约操作
        int sum = map.reduceValues(1, AtomicInteger::get, Integer::sum);
        System.out.println("\n所有值的和: " + sum);
    }

    /**
     * CopyOnWriteArrayList - 写时复制列表
     * 特点：读操作无锁，写时复制新数组
     * 适用：读多写少的场景
     */
    private static void copyOnWriteArrayListDemo() throws InterruptedException {
        System.out.println("【CopyOnWriteArrayList 示例】");

        List<String> list = new CopyOnWriteArrayList<>();
        list.addAll(Arrays.asList("A", "B", "C", "D", "E"));

        // 读操作 - 无锁，高性能
        System.out.println("初始列表: " + list);

        // 写操作 - 会复制整个数组，成本较高
        list.add("F");
        System.out.println("添加F后: " + list);

        // 并发读多写少演示
        CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>();
        cowList.addAll(Arrays.asList(1, 2, 3, 4, 5));

        AtomicInteger readCount = new AtomicInteger(0);

        // 创建100个读线程
        List<Thread> readers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            readers.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    // 读操作不需要加锁
                    int size = cowList.size();
                    Integer first = cowList.get(0);
                    readCount.incrementAndGet();
                }
            }));
        }

        // 创建1个写线程
        Thread writer = new Thread(() -> {
            for (int i = 6; i <= 10; i++) {
                cowList.add(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // 启动所有线程
        writer.start();
        for (Thread t : readers) {
            t.start();
        }

        // 等待所有线程完成
        for (Thread t : readers) {
            t.join();
        }
        writer.join();

        System.out.println("\n读操作次数: " + readCount.get());
        System.out.println("最终列表: " + cowList);
    }

    /**
     * CopyOnWriteArraySet - 写时复制集合
     * 底层使用 CopyOnWriteArrayList
     */
    private static void copyOnWriteArraySetDemo() {
        System.out.println("【CopyOnWriteArraySet 示例】");

        Set<String> set = new CopyOnWriteArraySet<>();

        // 添加元素（自动去重）
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Apple");  // 重复不会被添加
        System.out.println("Set: " + set);

        // 并发遍历时可以安全修改
        Thread iterator = new Thread(() -> {
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String item = it.next();
                System.out.println("遍历: " + item);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread modifier = new Thread(() -> {
            try {
                Thread.sleep(800);
                set.add("Durian");
                System.out.println("添加 Durian");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        iterator.start();
        modifier.start();
    }

    /**
     * ConcurrentLinkedQueue - 无锁并发队列
     * 特点：CAS实现，无锁，高性能
     * 适用：高并发队列场景
     */
    private static void concurrentLinkedQueueDemo() throws InterruptedException {
        System.out.println("【ConcurrentLinkedQueue 示例】");

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        // 多个生产者
        int producerCount = 5;
        int itemsPerProducer = 1000;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(producerCount);

        for (int i = 0; i < producerCount; i++) {
            final int producerId = i;
            new Thread(() -> {
                try {
                    startLatch.await();  // 等待所有线程就绪
                    for (int j = 0; j < itemsPerProducer; j++) {
                        queue.offer("P" + producerId + "-T" + j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        long start = System.nanoTime();
        startLatch.countDown();  // 开始生产
        endLatch.await();
        long duration = (System.nanoTime() - start) / 1_000_000;

        System.out.println("生产者完成");
        System.out.println("队列大小: " + queue.size());
        System.out.println("耗时: " + duration + " ms");

        // 多个消费者
        int consumerCount = 5;
        AtomicInteger consumedCount = new AtomicInteger(0);

        for (int i = 0; i < consumerCount; i++) {
            new Thread(() -> {
                while (true) {
                    String item = queue.poll();
                    if (item == null && queue.isEmpty()) {
                        break;
                    }
                    if (item != null) {
                        consumedCount.incrementAndGet();
                    }
                }
            }).start();
        }

        Thread.sleep(1000);
        System.out.println("消费者完成");
        System.out.println("已消费: " + consumedCount.get());
        System.out.println("队列剩余: " + queue.size());
    }

    /**
     * ArrayBlockingQueue - 有界阻塞队列
     * 特点：有界，满时阻塞，空时阻塞
     * 适用：生产者-消费者模式，限流
     */
    private static void arrayBlockingQueueDemo() throws InterruptedException {
        System.out.println("【ArrayBlockingQueue 示例】");

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        AtomicInteger produced = new AtomicInteger(0);
        AtomicInteger consumed = new AtomicInteger(0);

        // 生产者 - 生产速度快，会触发阻塞
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    queue.put(i);  // 如果队列满，会阻塞
                    produced.incrementAndGet();
                    System.out.println("生产: " + i + " (队列: " + queue.size() + ")");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 消费者 - 消费速度慢
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(300);
                    int item = queue.take();  // 如果队列空，会阻塞
                    consumed.incrementAndGet();
                    System.out.println("        消费: " + item + " (队列: " + queue.size() + ")");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("\n生产: " + produced.get() + ", 消费: " + consumed.get());

        // 应用场景：限流器
        System.out.println("\n【限流器示例】");
        RateLimiter rateLimiter = new RateLimiter(3);  // 每秒3个请求

        for (int i = 1; i <= 10; i++) {
            rateLimiter.acquire();
            System.out.println("处理请求 " + i);
        }
    }

    /**
     * 简单的限流器（基于阻塞队列）
     */
    static class RateLimiter {
        private final BlockingQueue<Object> bucket;
        private final ScheduledExecutorService scheduler;

        public RateLimiter(int rate) {
            this.bucket = new ArrayBlockingQueue<>(rate);
            this.scheduler = Executors.newSingleThreadScheduledExecutor();

            // 定时补充令牌
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    bucket.offer(new Object());
                } catch (Exception e) {
                    // 忽略
                }
            }, 0, 1000 / rate, TimeUnit.MILLISECONDS);
        }

        public void acquire() {
            try {
                bucket.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void shutdown() {
            scheduler.shutdown();
        }
    }
}
