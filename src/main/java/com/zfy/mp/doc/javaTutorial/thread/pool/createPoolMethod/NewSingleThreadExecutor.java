package com.zfy.bwcj.javaTutorial.thread.pool.createPoolMethod;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: NewSingleThreadExecutor.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool
 * @描述: 单线程化线程池
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 17:00
 * @版本号: V2.4.0
 */
public class NewSingleThreadExecutor {
    public static final int SLEEP_GAP=1000;
    static class TargetTask implements Runnable{
        static AtomicInteger taskNo=new AtomicInteger(1);
        private String taskName;
        private String executeMethod;
        public TargetTask()
        {
            taskName=  "task-"+taskNo;
            taskNo.incrementAndGet();
        }
        void setExecuteMethod(String method) {
            this.executeMethod = method;
        }
        public void run()
        {
            System.out.println("method: " + executeMethod + "; task:"+taskName+" is doing...");
            try {
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("method: " + executeMethod + "; task:"+taskName+" end...");
        }
    }
    public static void main(String[] args) {
        ExecutorService pool=Executors.newSingleThreadExecutor();
        for(int i=0;i<3;i++)
        {
            TargetTask t1 = new TargetTask();
            t1.setExecuteMethod("execute");
            pool.execute(t1);

            TargetTask t2 = new TargetTask();
            t2.setExecuteMethod("submit");
            pool.submit(t2);
        }
        pool.shutdown();
    }

}
