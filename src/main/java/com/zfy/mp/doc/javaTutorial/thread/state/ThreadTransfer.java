package com.zfy.bwcj.javaTutorial.thread.state;

import static java.lang.Thread.sleep;

/**
 * @文件名: ThreadTransfer.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.state
 * @描述: 线程状态改变
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 11:02
 * @版本号: V2.4.0
 */
public class ThreadTransfer {
    // state transfer image
    // https://i-blog.csdnimg.cn/blog_migrate/ebd7387baaae8328c2db9239fcbf3948.png
    /**
     *线程的所有状态
     *NEW:安排了工作,还未开始行动
     *RUNNABLE:可工作的.又可以分成正在工作中和即将开始工作.
     *BLOCKED:这几个都表示排队等着其他事情 阻塞
     *WAITING:这几个都表示排队等着其他事情  等待
     *TIMED_WAITING:这几个都表示排队等着其他事情 超时等待
     *TERMINATED:工作完成了
     ****/
    public static void main(String[] args) {
        joinThread();

    }

    //  NEW 、 RUNNABLE 、 TERMINATED 状态的转换
    public static void transfer1() {
        Thread t1 = new Thread(()  -> {
            for (int i = 0; i < 10; i++) {
            }
        }, "李四");
        System.out.println(t1.getName() + ": " + t1.getState() );
        t1.start();
        while (t1.isAlive()) {
            System.out.println(t1.getName() + ": " + t1.getState() );
        }
        System.out.println(t1.getName() + ": " + t1.getState() );
    }

    // WAITING 、 BLOCKED 、 TIMED_WAITING 状态的转换
    public static void transfer2() {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1");
        System.out.println(t1.getName() + ": " + t1.getState()); // 初始状态 NEW
        t1.start();

        // 等待 t1 启动并运行
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(t1.getName() + ": " + t1.getState()); // 此时 t1 应该是 RUNNABLE (刚进同步块) 或 TIMED_WAITING (如果运气好碰到了 sleep)

        // 等待足够长的时间，确保 t1 执行到了 sleep
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(t1.getName() + ": " + t1.getState()); // 此时 t1 肯定是 TIMED_WAITING

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("hehe");
            }
        }, "t2");

        System.out.println(t2.getName() + ": " + t2.getState()); // 初始状态 NEW
        t2.start();

        // 等待 t2 启动并尝试获取锁
        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(t2.getName() + ": " + t2.getState()); // 此时 t2 肯定是 BLOCKED，因为 t1 持有锁

    }

    /****
     * static void yield()	让位方法，当前线程暂停，回到就绪状态，让给其它线程。
     * void join()	将一个线程合并到当前线程中，当前线程受阻塞，加入的线程执行直到结束
     */

    public static void yieldThread() {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                //每100个让位一次。
                if (i % 100 == 0) {
//                    Thread.yield(); // 当前线程暂停一下，让给主线程。  注意： 并不是每次都让成功的，有可能它又抢到时间片了。
                }
                System.out.println(Thread.currentThread().getName() + " ---> " + i);
            }
        }, "t");
        t.start();
        for(int i = 1; i <= 10000; i++) {
            System.out.println(Thread.currentThread().getName() + "--->" + i);
        }
    }

    public static void joinThread() {
        System.out.println("main thread begin");
        Thread joinThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " ---> " + i);
            }
        }, "joinThread");
        joinThread.start();
        try {
            joinThread.join(); // 变同步 ，主线程阻塞joinThread线程执行完再执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread over");
    }




}
