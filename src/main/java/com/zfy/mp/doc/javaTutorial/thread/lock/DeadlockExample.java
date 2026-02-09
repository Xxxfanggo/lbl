package com.zfy.bwcj.javaTutorial.thread.lock;

public class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock1");
            synchronized (lock2) {
                System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock2");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock2");
            synchronized (lock1) {
                System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock1");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockExample deadlock = new DeadlockExample();

        Thread t1 = new Thread(deadlock::method1, "Thread-1");
        Thread t2 = new Thread(deadlock::method2, "Thread-2");

        t1.start();
        t2.start();

    }
}
