package com.zfy.bwcj.javaTutorial.thread.consumerAndProducer;

import java.util.List;

/**
 * @文件名: Producer.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.consumerAndProducer
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 16:25
 * @版本号: V2.4.0
 */
public class Producer implements Runnable{
    private List list;
    public Producer(List list) {
        this.list = list;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                System.out.println("开始生成。。。。。。。");
                if (list.size() > 0) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 程序能够执行到这里说明仓库是空的，可以生产
                list.add("rice");
                try {
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "生产了一个产品 --->" + list.get(0));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.notifyAll();
            }
        }

    }
}
