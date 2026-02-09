package com.zfy.mp.doc.javaTutorial.thread.localThread;

/**
 * @文件名: TaskStock.java
 * @工程名: bwcj-back
 * @包名: com.zfy.mp.doc.javaTutorial.thread.localThread
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-09 16:07
 * @版本号: V2.4.0
 */
public class TaskStock {
    // 库存数量
    public static int TOTAL_STOCK = 1;

    public static void main(String[] args) {
        // 模拟 2 个线程同时领取
        int numThreads = 2;
        for (int i = 1; i <= numThreads; i++) {
            new Thread(() -> {
                while (true) {
                    // 模拟业务操作耗时
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 如果库存小于等于0，代表任务已经领完
                    if (TaskStock.TOTAL_STOCK <= 0) {
                        System.out.println(Thread.currentThread().getName() + ": 任务领取失败×");
                        break;
                    }
                    // 库存扣减
                    TaskStock.TOTAL_STOCK = TaskStock.TOTAL_STOCK - 1;
                    System.out.println(Thread.currentThread().getName() + ": 任务领取成功√");
                }
            }, "Thread-" + i).start();
        }

    }
}
