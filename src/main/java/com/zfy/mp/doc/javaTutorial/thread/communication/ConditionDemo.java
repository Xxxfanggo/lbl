package com.zfy.bwcj.javaTutorial.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @文件名: ConditionDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.communication
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-21 15:44
 * @版本号: V2.4.0
 */
public class ConditionDemo {
    private Integer number = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        lock.lock();
        try {
            while (number != 0) {
                // this.await();
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + " " + number);
            // this.notifyAll()
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                // this.await();
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + " " + number);
            // this.notifyAll()
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionDemo conditionDemo = new ConditionDemo();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                conditionDemo.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                conditionDemo.decrement();
            }
        }, "B").start();
    }
}
