package com.zfy.bwcj.javaTutorial.thread.daemon;

/**
 * @文件名: Thread_Daemon.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.daemon
 * @描述: 后台线程
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 15:39
 * @版本号: V2.4.0
 */
public class Thread_Daemon {
    public static void main(String[] args) {
        DeamonThread t1 = new DeamonThread();
        t1.setDaemon(true);
        t1.setName("t1");
        t1.start();
        // 主线程
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "--->" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class DeamonThread extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                System.out.println(Thread.currentThread().getName() + "--->" + (++i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
