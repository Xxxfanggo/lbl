package com.zfy.mp.doc.javaTutorial.collections.queue;

import java.util.*;
import java.util.concurrent.*;

/**
 * @文件名: QueueDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.mp.doc.javaTutorial.collections.queue
 * @描述: Queue 接口及实现类示例
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-22
 * @版本号: V2.4.0
 */
public class QueueDemo {

    public static void main(String[] args) throws InterruptedException {
        linkedListQueue();
        System.out.println("\n" + "=".repeat(50) + "\n");
        arrayDeque();
        System.out.println("\n" + "=".repeat(50) + "\n");
        priorityQueue();
        System.out.println("\n" + "=".repeat(50) + "\n");
        blockingQueue();
        System.out.println("\n" + "=".repeat(50) + "\n");
        dequeAsStack();
    }

    /**
     * LinkedList 作为队列使用
     */
    private static void linkedListQueue() {
        System.out.println("【LinkedList 队列示例】");

        Queue<String> queue = new LinkedList<>();

        // 添加元素
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("队列: " + queue);

        // 查看队首元素（不删除）
        System.out.println("peek: " + queue.peek());

        // 出队（删除并返回队首）
        System.out.println("poll: " + queue.poll());
        System.out.println("poll: " + queue.poll());
        System.out.println("剩余队列: " + queue);

        // 检查队列状态
        System.out.println("队列大小: " + queue.size());
        System.out.println("是否为空: " + queue.isEmpty());
    }

    /**
     * ArrayDeque - 数组双端队列
     * 特点：比 LinkedList 更高效的栈/队列实现
     */
    private static void arrayDeque() {
        System.out.println("【ArrayDeque 示例】");

        Deque<Integer> deque = new ArrayDeque<>();

        // 从队列头部操作
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        System.out.println("头部添加后: " + deque);  // [3, 2, 1]

        // 从队列尾部操作
        deque.addLast(4);
        deque.addLast(5);
        System.out.println("尾部添加后: " + deque);  // [3, 2, 1, 4, 5]

        // 获取元素
        System.out.println("getFirst: " + deque.getFirst());
        System.out.println("getLast: " + deque.getLast());

        // 删除元素
        deque.removeFirst();
        deque.removeLast();
        System.out.println("删除首尾后: " + deque);

        // 作为栈使用
        Deque<String> stack = new ArrayDeque<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        System.out.println("\n作为栈:");
        while (!stack.isEmpty()) {
            System.out.println("  弹出: " + stack.pop());
        }
    }

    /**
     * PriorityQueue - 优先级队列
     * 特点：基于堆实现，元素按优先级排序
     * 适用：任务调度、优先队列场景
     */
    private static void priorityQueue() {
        System.out.println("【PriorityQueue 示例】");

        // 默认按自然顺序（从小到大）
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(30);
        minHeap.offer(10);
        minHeap.offer(50);
        minHeap.offer(20);
        System.out.println("最小堆（默认）: " + minHeap);

        System.out.print("按优先级取出: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }

        // 最大堆（自定义比较器）
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(30);
        maxHeap.offer(10);
        maxHeap.offer(50);
        maxHeap.offer(20);
        System.out.println("\n\n最大堆（自定义）: " + maxHeap);

        System.out.print("按优先级取出: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }

        // 自定义对象优先级
        PriorityQueue<Task> taskQueue = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority));
        taskQueue.offer(new Task("低优先级", 3));
        taskQueue.offer(new Task("高优先级", 1));
        taskQueue.offer(new Task("中优先级", 2));

        System.out.println("\n\n任务队列（按优先级）:");
        while (!taskQueue.isEmpty()) {
            System.out.println("  " + taskQueue.poll());
        }

        // 按优先级+时间排序
        PriorityQueue<ScheduledTask> scheduledQueue = new PriorityQueue<>(
                Comparator.comparingInt(ScheduledTask::getPriority)
                        .thenComparingLong(ScheduledTask::getTime)
        );
        scheduledQueue.offer(new ScheduledTask("任务A", 2, 100));
        scheduledQueue.offer(new ScheduledTask("任务B", 1, 200));
        scheduledQueue.offer(new ScheduledTask("任务C", 2, 50));

        System.out.println("\n调度队列（优先级+时间）:");
        while (!scheduledQueue.isEmpty()) {
            System.out.println("  " + scheduledQueue.poll());
        }
    }

    static class Task {
        private String name;
        private int priority;  // 1=高, 2=中, 3=低

        public Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        public int getPriority() { return priority; }

        @Override
        public String toString() {
            return String.format("%s (优先级:%d)", name, priority);
        }
    }

    static class ScheduledTask {
        private String name;
        private int priority;
        private long time;

        public ScheduledTask(String name, int priority, long time) {
            this.name = name;
            this.priority = priority;
            this.time = time;
        }

        public int getPriority() { return priority; }
        public long getTime() { return time; }

        @Override
        public String toString() {
            return String.format("%s (优先级:%d, 时间:%d)", name, priority, time);
        }
    }

    /**
     * 阻塞队列（BlockingQueue）- 生产者消费者模式
     */
    private static void blockingQueue() throws InterruptedException {
        System.out.println("【阻塞队列示例】");

        // ArrayBlockingQueue - 有界阻塞队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // 生产者
        Thread producer = new Thread(() -> {
            String[] items = {"A", "B", "C", "D", "E"};
            for (String item : items) {
                try {
                    System.out.println("生产者: 生产 " + item);
                    queue.put(item);  // 如果队列满，会阻塞
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // 消费者
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    String item = queue.take();  // 如果队列空，会阻塞
                    System.out.println("消费者: 消费 " + item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("\n其他阻塞队列:");
        System.out.println("  - LinkedBlockingQueue: 可选边界的阻塞队列");
        System.out.println("  - PriorityBlockingQueue: 带优先级的无界阻塞队列");
        System.out.println("  - SynchronousQueue: 不存储元素的阻塞队列（每个put必须等待take）");
        System.out.println("  - DelayQueue: 延时获取元素的无界阻塞队列");
    }

    /**
     * Deque 作为双端队列使用
     */
    private static void dequeAsStack() {
        System.out.println("【Deque 作为栈和队列】");

        Deque<String> deque = new ArrayDeque<>();

        // ===== 作为栈使用（LIFO）=====
        System.out.println("作为栈（LIFO）:");
        deque.push("A");
        deque.push("B");
        deque.push("C");
        System.out.println("栈: " + deque);

        System.out.print("弹出: ");
        while (!deque.isEmpty()) {
            System.out.print(deque.pop() + " ");
        }

        // ===== 作为队列使用（FIFO）=====
        System.out.println("\n\n作为队列（FIFO）:");
        deque.offer("X");
        deque.offer("Y");
        deque.offer("Z");
        System.out.println("队列: " + deque);

        System.out.print("出队: ");
        while (!deque.isEmpty()) {
            System.out.print(deque.poll() + " ");
        }
    }
}
