package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 风控系统观察者
 * 监控异常价格波动
 */
public class RiskControlObserver implements Observer {

    private double maxDailyChange;   // 最大单日波动百分比
    private int alertCount;          // 警告计数器
    private List<RiskAlert> alerts;  // 警告历史

    /**
     * 风险警告记录
     */
    static class RiskAlert {
        String symbol;
        double price;
        double changePercent;
        LocalDateTime timestamp;

        RiskAlert(String symbol, double price, double changePercent) {
            this.symbol = symbol;
            this.price = price;
            this.changePercent = changePercent;
            this.timestamp = LocalDateTime.now();
        }
    }

    /**
     * 构造方法
     *
     * @param maxDailyChange 最大单日波动百分比（如 8 表示 8%）
     */
    public RiskControlObserver(double maxDailyChange) {
        this.maxDailyChange = maxDailyChange;
        this.alertCount = 0;
        this.alerts = new ArrayList<>();
    }

    @Override
    public void update(String stockSymbol, double newPrice, double changePercent) {
        double absChangePercent = Math.abs(changePercent);

        System.out.println("🛡️ 【风控系统】监控中");
        System.out.println("   当前波动: " + String.format("%.2f%%", absChangePercent));
        System.out.println("   风险阈值: " + maxDailyChange + "%");

        if (absChangePercent > maxDailyChange) {
            // 触发风险预警
            triggerAlert(stockSymbol, newPrice, changePercent);
        } else {
            System.out.println("   ✅ 波动在正常范围内");
        }
    }

    /**
     * 触发风险预警
     *
     * @param stockSymbol 股票代码
     * @param price 当前价格
     * @param changePercent 变化百分比
     */
    private void triggerAlert(String stockSymbol, double price, double changePercent) {
        alertCount++;

        // 记录警告
        alerts.add(new RiskAlert(stockSymbol, price, changePercent));

        System.out.println("   ⚠️⚠️⚠️ 风险预警触发！⚠️⚠️⚠️");
        System.out.println("   📊 波动详情:");
        System.out.println("      股票代码: " + stockSymbol);
        System.out.println("      当前价格: ¥" + String.format("%.2f", price));
        System.out.println("      涨跌幅: " + String.format("%+.2f%%", changePercent));
        System.out.println("   📈 累计预警次数: " + alertCount);
    }

    /**
     * 获取累计预警次数
     *
     * @return 预警次数
     */
    public int getAlertCount() {
        return alertCount;
    }

    /**
     * 打印所有警告记录
     */
    public void printAlertHistory() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           风控预警历史记录");
        System.out.println("=".repeat(50));

        for (int i = 0; i < alerts.size(); i++) {
            RiskAlert alert = alerts.get(i);
            System.out.println((i + 1) + ". " + alert.timestamp);
            System.out.println("   股票: " + alert.symbol +
                ", 价格: ¥" + String.format("%.2f", alert.price) +
                ", 波动: " + String.format("%+.2f%%", alert.changePercent));
        }

        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public String getObserverName() {
        return "风控系统";
    }
}
