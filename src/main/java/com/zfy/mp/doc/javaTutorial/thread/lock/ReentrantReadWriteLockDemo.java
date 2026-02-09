package com.zfy.bwcj.javaTutorial.thread.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * @文件名: ReentrantReadWriteLockDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.lock
 * @描述: 读写锁
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-21 11:16
 * @版本号: V2.4.0
 */
public class ReentrantReadWriteLockDemo {




    public static void main(String[] args) {

        // 创建 MyCache 实例
        MyCache cache = new MyCache();

        // 启动 5 个写线程
        for (int i = 1; i <= 5; i++) {
            String num = String.valueOf(i);
            new Thread(() -> {
                cache.put(num, num);
            }, num).start();
        }

        // 启动 5 个读线程
        for (int i = 1; i <= 5; i++) {
            String num = String.valueOf(i);
            new Thread(() -> {
                cache.get(num);
            }, num).start();
        }
    }
}

