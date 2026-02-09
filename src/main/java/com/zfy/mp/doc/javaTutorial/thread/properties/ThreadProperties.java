package com.zfy.bwcj.javaTutorial.thread.properties;

/**
 * @文件名: ThreadProperties.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.` properties`
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 10:41
 * @版本号: V2.4.0
 */
class ThreadProperties {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("Thread name: " + Thread.currentThread().getName());
                        // 休眠当前线程
//                        Thread.sleep(1 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + ": 我即将死去");
            }
        };
        System.out.println(Thread.currentThread().getName() + ": ID: " + thread.getId());
        System.out.println(Thread.currentThread().getName() + ": 名称: " + thread.getName());
        System.out.println(Thread.currentThread().getName() + ": 状态: " + thread.getState());
        //最低优先级1
        //默认优先级是5
        //最高优先级10  优先级比较高的获取CPU时间片可能会多一些。（但也不完全是，大概率是多的。）
        thread.setPriority(1);
        System.out.println(Thread.currentThread().getName() + ": 优先级: " + thread.getPriority());
        // 后台线程（守护线程， eg: 垃圾回收线程）
        // void setDaemon(boolean on)	on为true表示把线程设置为守护线程
        System.out.println(Thread.currentThread().getName() + ": 后台线程: " + thread.isDaemon());
        System.out.println(Thread.currentThread().getName() + ": 活着: " + thread.isAlive());
        System.out.println(Thread.currentThread().getName() + ": 被中断: " + thread.isInterrupted());
        thread.start();
        Thread thread1 = Thread.currentThread();
        System.out.println("当前线程的引用:" + Thread.currentThread().getName() + ": ID: " + thread1.getId());
    }
}