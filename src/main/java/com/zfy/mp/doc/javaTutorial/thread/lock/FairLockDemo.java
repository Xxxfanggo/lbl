package com.zfy.bwcj.javaTutorial.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @文件名: FairLockDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.lock
 * @描述: 公平锁
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-20 17:14
 * @版本号: V2.4.0
 */
public class FairLockDemo {
    /**
     * ReentrantLock还可以实现公平锁。所谓公平锁，也就是在锁上等待时间最长的线程优先获得锁的使用权。通俗的理解就是谁排队时间最长谁先获取锁。（公平锁不允许插队，非公平锁允许插队）
     */
    // 创建一个公平锁的 ReentrantLock 实例，true 参数表示启用公平性
    private final ReentrantLock lock = new ReentrantLock(true);

    public void doSomething() {
        lock.lock();
        try {
            // 执行需要同步的代码
            System.out.println(Thread.currentThread().getName() + " is doing something.");
            Thread.sleep(200);
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        FairLockDemo demo = new FairLockDemo();
        // 创建 5 个线程，每个线程都会调用 doSomething 方法，争夺锁
        for (int i = 1; i <= 5; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    demo.doSomething();
                }
            }, "线程-" + i);
            t.start();
        }

    }
}
