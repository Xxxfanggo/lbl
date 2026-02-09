package com.zfy.bwcj.javaTutorial.thread.createMethod;

/**
 * @文件名: ImplThread.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.createMethod
 * @描述: 实现方式创建
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 10:31
 * @版本号: V2.4.0
 */
public class ImplRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            System.out.println("实现方式创建线程");
        }
    }
}
