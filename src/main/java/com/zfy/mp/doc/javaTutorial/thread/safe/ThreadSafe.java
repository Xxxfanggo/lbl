package com.zfy.bwcj.javaTutorial.thread.safe;

import com.zfy.bwcj.javaTutorial.thread.safe.DTO.Account;
import jakarta.annotation.Resource;

/**
 * @文件名: AccountThread.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.thread.safe.DTO
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-01-19 14:57
 * @版本号: V2.4.0
 */
public class ThreadSafe {
    public static void main(String[] args) {
        Account account = new Account();
        account.setAccountName("zfy");
        account.setBalance(10000d);
        AccountThread at1 = new AccountThread(account);
        AccountThread at2 = new AccountThread(account);
        at1.setName("t1");
        at2.setName("t2");
        at1.start();
        at2.start();

    }
}
