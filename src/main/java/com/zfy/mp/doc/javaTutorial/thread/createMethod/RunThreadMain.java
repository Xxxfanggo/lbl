package com.zfy.bwcj.javaTutorial.thread.createMethod;

/**
 * @文件名: runThreadMain.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.createMethod
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 10:28
 * @版本号: V2.4.0
 */
public class RunThreadMain {

    public static void main(String[] args) {
        // 继承创建
        ExtendThread extendThread = new ExtendThread();
//        extendThread.run();
        extendThread.start();
        // 实现创建
        Thread implRunnable = new Thread(new ImplRunnable());
        implRunnable.start();
        // 匿名内部类创建
        Thread anonymousInner = new Thread()  {
            @Override
                    public void run() {
                System.out.println("匿名内部类创建线程");
            }
        };
        anonymousInner.start();
        // lambda表达式创建
        Thread lambdaThread1 = new Thread(() -> System.out.println("lambda表达式创建线程1"));
        Thread lambdaThread2 = new Thread(() -> {
            System.out.println("lambda表达式创建线程2");
        });
        lambdaThread1.start();
        lambdaThread2.start();
    }


}
