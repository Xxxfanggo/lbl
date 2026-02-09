package com.zfy.bwcj.javaTutorial.thread.consumerAndProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * @文件名: Factory.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.consumerAndProducer
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 16:28
 * @版本号: V2.4.0
 */
public class Factory {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        Thread t1 = new Thread(new Consumer(list), "消费者线程");
        Thread t2 = new Thread(new Producer(list), "生产者线程");
        t1.start();
        t2.start();
    }
}
