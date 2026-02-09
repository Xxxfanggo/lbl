package com.zfy.bwcj.javaTutorial.thread.createMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @文件名: CallableThread
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.createMethod
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 16:08
 * @版本号: V2.4.0
 */
public class CallableThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                // 线程执行一个任务，执行之后可能会有一个执行结果
                // 模拟执行
                System.out.println("call method begin");
                Thread.sleep(1000 * 10);
                System.out.println("call method end!");
                int a = 100;
                int b = 200;
                return a + b; //自动装箱(300结果变成Integer)
            }
        });

        Thread t = new Thread(futureTask);
        t.start();
        // 这里是main方法，这是在主线程中。
        // 在主线程中，怎么获取t线程的返回结果？
        // get()方法的执行会导致“当前线程阻塞”
        Object o = futureTask.get(); // 阻塞式获取结果
        System.out.println("线程执行结果：" + o);
        // main方法这里的程序要想执行必须等待get()方法的结束
        // 而get()方法可能需要很久。因为get()方法是为了拿另一个线程的执行结果
        // 另一个线程执行是需要时间的。
        System.out.println("main over");
    }
}
