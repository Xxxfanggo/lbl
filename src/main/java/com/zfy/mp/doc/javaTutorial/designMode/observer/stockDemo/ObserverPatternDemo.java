package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo;

import com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer.AnalystObserver;
import com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer.RiskControlObserver;
import com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer.TraderObserver;
import com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.subject.Stock;

/**
 * 观察者模式演示
 */
public class ObserverPatternDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║             📈 观察者模式 - 股票价格推送系统               ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // ── 创建被观察者 ──
        Stock tencentStock = new Stock("0700", "腾讯控股", 350.00);

        // ── 创建观察者 ──
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                    注册观察者");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        TraderObserver trader1 = new TraderObserver("张三", 340.00, 380.00, 100);
        TraderObserver trader2 = new TraderObserver("李四", 320.00, 360.00, 200);
        AnalystObserver analyst = new AnalystObserver("王分析师", "APP推送");
        RiskControlObserver riskControl = new RiskControlObserver(8.0);

        // ── 注册观察者 ──
        tencentStock.registerObserver(trader1);
        tencentStock.registerObserver(trader2);
        tencentStock.registerObserver(analyst);
        tencentStock.registerObserver(riskControl);

        System.out.println("\n📊 当前观察者数量: " + tencentStock.getObserverCount() + "\n");

        // ── 模拟价格变化 ──
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                    模拟价格变化");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        tencentStock.setPrice(345.50);   // 小幅下跌
        tencentStock.setPrice(341.00);   // 接近交易员A目标价
        tencentStock.setPrice(339.50);   // 触发交易员A买入
        tencentStock.setPrice(329.80);   // 触发交易员B买入
        tencentStock.setPrice(280.00);   // 大幅下跌，触发风控
        tencentStock.setPrice(320.00);   // 回升

        // ── 查看风控历史 ──
        riskControl.printAlertHistory();

        // ── 移除观察者 ──
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("                    移除观察者");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        tencentStock.removeObserver(trader1);
        System.out.println("📊 剩余观察者数量: " + tencentStock.getObserverCount() + "\n");

        // ── 再次变化，交易员A不会收到通知 ──
        tencentStock.setPrice(335.00);
    }
}
