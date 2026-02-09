package com.zfy.mp.doc.javaTutorial.thread.createMethod;

import org.springframework.stereotype.Component;

/**
 *
 * @文件名: extandThread.java
 * @工程名: mp
 * @包名: com.zfy.mp.doc.javaTutorial.thread.createMethod
 * @描述: 继承创建
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 10:24
 * @版本号: V2.4.0
 */
@Component
public class ExtendThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            System.out.println("继承方式创建线程");
        }
    }
}
