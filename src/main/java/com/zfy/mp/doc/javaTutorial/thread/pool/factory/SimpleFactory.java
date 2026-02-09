package com.zfy.bwcj.javaTutorial.thread.pool.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: SimpleFactory.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool.factory
 * @描述: 线程工厂
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-20 14:55
 * @版本号: V2.4.0
 */
public class SimpleFactory {
    public static final int SLEEP_GAP=1000;

    static class SimpleThreadFactory implements ThreadFactory {
        static AtomicInteger threadNo=new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable task) {
            String threadName="simpleThread-"+threadNo;
            System.out.println("创建一条线程，名字是："+threadName);
            threadNo.incrementAndGet();
            Thread thread = new Thread(task, threadName);
            thread.setDaemon(true);
            return thread;
        }
    }
    static class TargetTask implements Runnable{
        static AtomicInteger taskNo=new AtomicInteger(1);
        String taskName;
        public TargetTask()
        {
            taskName="task-"+taskNo;
            taskNo.incrementAndGet();
        }
        public void run()
        {

            System.out.println(Thread.currentThread().getName()+": "+taskName+" is doing...");

            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(taskName+" end...");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5,new SimpleThreadFactory());
        for(int i=0;i<5;i++)
        {
            pool.submit(new TargetTask());
        }
        Thread.sleep(5000);
        pool.shutdown();
    }
}
