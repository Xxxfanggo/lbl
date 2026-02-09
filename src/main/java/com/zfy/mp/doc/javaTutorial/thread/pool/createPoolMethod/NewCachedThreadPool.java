package com.zfy.bwcj.javaTutorial.thread.pool.createPoolMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @文件名: newCachedThreadPool.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.pool.createPoolMethod
 * @描述: 可缓存线程池
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-20 11:17
 * @版本号: V2.4.0
 */
public class NewCachedThreadPool {
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
        private void setExecuteMethod(String method) {
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
        ExecutorService pool= Executors.newCachedThreadPool();
        for(int i=0;i<3;i++)
        {
            NewCachedThreadPool.TargetTask t1 = new NewCachedThreadPool.TargetTask();
            t1.setExecuteMethod("execute");
            pool.execute(t1);

            NewCachedThreadPool.TargetTask t2 = new NewCachedThreadPool.TargetTask();
            t2.setExecuteMethod("submit");
            pool.submit(t2);
        }
        pool.shutdown();
    }
}
