package com.zfy.bwcj.javaTutorial.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample2 {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    public static void main(String[] args) {
        // 创建并启动线程1
        new Thread(() -> {

            // 尝试获取 lock1 锁
            boolean result1 = lock1.tryLock();
            if (result1){
                try {
                    // 成功获取 lock1 锁，输出提示信息
                    System.out.println("线程1: 持有锁1...");

                    // 模拟一些工作
                    try {
                        Thread.sleep(500); // 休眠 500 毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 输出提示信息，表示线程1正在等待获取 lock2 锁
                    System.out.println("线程1: 等待获取锁2...");

                    // 尝试获取 lock2 锁
                    boolean result2 = lock2.tryLock();
                    if(result2){
                        try {
                            // 成功获取 lock2 锁，输出提示信息
                            System.out.println("线程1: 成功获取到两个锁。");
                        } finally {
                            // 确保最终释放 lock2 锁
                            lock2.unlock();
                        }
                    }else {
                        // 获取 lock2 失败，输出提示信息
                        System.out.println("线程1: 未获取到锁2");
                    }

                }finally {
                    // 确保最终释放 lock1 锁
                    lock1.unlock();
                }
            }else{
                // 获取 lock1 失败，输出提示信息
                System.out.println("线程1: 未获取到锁1");
            }

        }).start(); // 启动线程1

        // 创建并启动线程2
        new Thread(() -> {

            // 尝试获取 lock2 锁
            boolean result2 = lock2.tryLock();

            if(result2){
                try {
                    // 成功获取 lock2 锁，输出提示信息
                    System.out.println("线程2: 持有锁2...");

                    // 模拟一些工作
                    try {
                        Thread.sleep(500); // 休眠 500 毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 输出提示信息，表示线程2正在等待获取 lock1 锁
                    System.out.println("线程2: 等待获取锁1...");

                    // 尝试获取 lock1 锁
                    boolean result1 = lock1.tryLock();
                    if(result1){
                        try {
                            // 成功获取 lock1 锁，输出提示信息
                            System.out.println("线程2: 成功获取到两个锁。");
                        } finally {
                            // 确保最终释放 lock1 锁
                            lock1.unlock();
                        }
                    }else {
                        // 获取 lock1 失败，输出提示信息
                        System.out.println("线程2: 未获取到锁1");
                    }
                }finally {
                    // 确保最终释放 lock2 锁
                    lock2.unlock();
                }
            }else{
                // 获取 lock2 失败，输出提示信息
                System.out.println("线程2: 未获取到锁2");
            }

        }).start(); // 启动线程2

    }
}
