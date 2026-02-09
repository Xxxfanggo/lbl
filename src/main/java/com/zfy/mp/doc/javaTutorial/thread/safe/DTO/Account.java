package com.zfy.bwcj.javaTutorial.thread.safe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @文件名: Account.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.safe
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 14:51
 * @版本号: V2.4.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountName;
    private double balance;
    //对象
    Object o= new Object();

    public void withDraw(double money) {
        /**
         * 以下可以共享,金额不会出错
         * 以下这几行代码必须是线程排队的，不能并发。
         * 一个线程把这里的代码全部执行结束之后，另一个线程才能进来。
         * synchronized (this) synchronized (action)  synchronized (o)
         */
        /**
         * 以下不共享，金额会出错
         */
		  /*Object obj = new Object();
	        synchronized(obj) { // 这样编写就不安全了。因为obj2不是共享对象。
	        synchronized(null) {//编译不通过
	        String s = null;
	        synchronized(s) {//java.lang.NullPointerException*/

        synchronized (this) {
            double before = this.getBalance();
            double after = before - money;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.balance = after;
        }
    }
}
