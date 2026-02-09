package com.zfy.bwcj.javaTutorial.thread.safe;

import com.zfy.bwcj.javaTutorial.thread.safe.DTO.Account;
import jakarta.annotation.Resource;

/**
 * @文件名: AccountThread.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.safe
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 15:01
 * @版本号: V2.4.0
 */
public class AccountThread extends Thread{
    private Account account;

    public AccountThread(Account account){
        this.account = account;
    }


    public void run(){
        double money = 5000;
        account.withDraw(money);
        System.out.println(Thread.currentThread().getName() + "对"+account.getAccountName()+"取款"+money+"成功，余额" + account.getBalance());
    }
}
