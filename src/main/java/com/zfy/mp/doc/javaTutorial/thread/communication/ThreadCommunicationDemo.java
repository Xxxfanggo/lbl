package com.zfy.bwcj.javaTutorial.thread.communication;

/**
 * @文件名: ThreadCommunicationDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.communication
 * @描述: 线程通信
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-21 14:48
 * @版本号: V2.4.0
 */
public class ThreadCommunicationDemo {

    // 用于协调两个线程的对象
    private final Object lock = new Object();
    // 当前打印的字符类型：true 表示数字线程，false 表示字母线程
    private boolean printNumber = true;

    private void startThread() {
        Thread numberThread = new Thread(() -> {
            for (int i = 1; i < 52; i++) {
                synchronized (lock)  {
                    while (!printNumber) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (printNumber) {
                        // 打印数字
                        System.out.print(i < 10 ? "0" + i : i);
                    }
                        // 处理1-9的格式
                    if (i % 2 == 0) {
                        System.out.print(""); // 每两个数字之间加空格
                    }
                    printNumber = false;
                    lock.notify(); // 通知字母线程
                }

            }
        }, "numberThread");

        Thread letterThread = new Thread(() -> {
            char letter = 'A';
            for (int i = 1; i <= 26; i++) {
                synchronized (lock) {
                    while (printNumber) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // 打印字母
                    System.out.print(letter + " ");
                    letter ++;
                    if (letter > 'Z') {
                        printNumber = false;
                    } else {
                        printNumber = true;
                    }
                    lock.notify();
                }
            }
        },"letterThread");
        numberThread.start();
        letterThread.start();
        // 等待线程完成
//        try {
//            numberThread.join();
//            letterThread.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

    }

    public static void main(String[] args) {
        new ThreadCommunicationDemo().startThread();
    }
}
