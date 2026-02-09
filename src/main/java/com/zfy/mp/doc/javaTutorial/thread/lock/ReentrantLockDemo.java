package com.zfy.bwcj.javaTutorial.thread.lock;

/**
 * @文件名: ReentrantLockDemo.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.lock
 * @描述: 可重入锁
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-20 16:56
 * @版本号: V2.4.0
 */
public class ReentrantLockDemo {
    // 方法 a 被 synchronized 关键字修饰，意味着该方法是同步的，
    // 在执行该方法时，需要获得当前对象的锁（this 对象的锁）。
    public synchronized void a() {
        // 在 a 方法中，首先调用了 b 方法
        // 因为 a 方法和 b 方法都被 synchronized 修饰，所以当调用 b 方法时，
        // 线程会试图重新获取当前对象的锁 (this)，
        // 由于 Java 支持可重入锁 (Reentrant Lock)，
        // 当前线程已经持有了 this 对象的锁，所以可以再次获取而不被阻塞。
        this.b();
        // 打印 "a"
        System.out.println("a");
    }

    // 方法 b 也是一个同步方法，同样需要获取 this 对象的锁
    public synchronized void b() {
        // 打印 "b"
        System.out.println("b");
    }

    public static void main(String[] args) {
        // 创建一个 ReentrantLockDemo 对象，并调用其 a() 方法
        // 当调用 a() 方法时，线程首先会获得当前 ReentrantLockDemo 对象的锁。
        // 然后 a() 方法内部会调用 b() 方法，当前线程再次尝试获取锁，
        // 由于可重入锁的特性，线程可以重复获取已经持有的锁。
        new ReentrantLockDemo().a();
    }
}
