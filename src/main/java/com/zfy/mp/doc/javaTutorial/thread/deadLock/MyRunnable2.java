package com.zfy.mp.doc.javaTutorial.thread.deadLock;

/**
 * @文件名: MyRunnable2.java
 * @工程名: mp
 * @包名: com.zfy.mp.doc.javaTutorial.thread.deadLock
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-09 17:13
 * @版本号: V2.4.0
 */
class MyRunnable2 implements Runnable {
    Dress dress;
    Trousers trousers;

    public MyRunnable2() {
    }

    public MyRunnable2(Dress dress, Trousers trousers) {
        this.dress = dress;
        this.trousers = trousers;
    }

    @Override
    public void run() {
        synchronized (trousers) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (dress) {
                System.out.println("。。。。。。。。。。。。。。");
            }
        }
    }
}
