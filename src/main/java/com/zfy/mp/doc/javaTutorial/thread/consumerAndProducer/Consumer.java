package com.zfy.bwcj.javaTutorial.thread.consumerAndProducer;

import java.util.List;

/**
 * @文件名: Consumer.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.consumerAndProducer
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 16:22
 * @版本号: V2.4.0
 */
public class Consumer implements Runnable{
    // 仓库
    private List list;

    public Consumer(List list) {
        this.list = list;
    }
    @Override
    public void run() {
        while(true) {
            synchronized (list) {
                System.out.println("开始消费。。。。。。");
                if (list.size() == 0) {
                    try {
                        // 当前线程等待，不再消费
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Object remove = list.remove(0);
                try {
                    Thread.sleep(3000);
                    System.out.println("消费者消费了一个产品：" + remove.toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 唤醒生产者
                list.notifyAll();
            }
        }
    }

}
