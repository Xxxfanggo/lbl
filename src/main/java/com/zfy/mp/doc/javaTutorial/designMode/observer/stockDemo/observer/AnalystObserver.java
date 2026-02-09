package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 分析师观察者
 * 分析股票走势并给出建议
 */
public class AnalystObserver implements Observer {

    private String name;              // 分析师姓名
    private String publishChannel;    // 报告发布渠道
    private List<String> reports;     // 历史报告

    /**
     * 构造方法
     *
     * @param name 分析师姓名
     * @param publishChannel 报告发布渠道
     */
    public AnalystObserver(String name, String publishChannel) {
        this.name = name;
        this.publishChannel = publishChannel;
        this.reports = new ArrayList<>();
    }

    @Override
    public void update(String stockSymbol, double newPrice, double changePercent) {
        System.out.println("📊 【" + name + "】分析中...");

        // 分析价格变化
        String analysis = analyzePriceChange(newPrice, changePercent);
        String recommendation = getRecommendation(changePercent);

        // 保存分析报告
        String report = buildReport(stockSymbol, newPrice, changePercent, analysis, recommendation);
        reports.add(report);

        // 发布报告
        publishReport(report);
    }

    /**
     * 分析价格变化
     *
     * @param price 当前价格
     * @param changePercent 变化百分比
     * @return 分析结论
     */
    private String analyzePriceChange(double price, double changePercent) {
        double absChange = Math.abs(changePercent);

        if (absChange > 10) {
            return "⚠️ 极端波动，市场情绪剧烈变化";
        } else if (absChange > 5) {
            return "📈 大幅" + (changePercent > 0 ? "上涨" : "下跌") + "，需密切关注";
        } else if (absChange > 2) {
            return "📊 " + (changePercent > 0 ? "稳步" : "温和") + (changePercent > 0 ? "上升" : "下跌") + "，趋势正常";
        } else {
            return "➖ 波动较小，横盘整理中";
        }
    }

    /**
     * 获取投资建议
     *
     * @param changePercent 变化百分比
     * @return 投资建议
     */
    private String getRecommendation(double changePercent) {
        if (changePercent > 8) {
            return "⚠️ 建议谨慎持有，注意风险";
        } else if (changePercent > 2) {
            return "✅ 建议继续持有";
        } else if (changePercent > -2) {
            return "⏸️ 建议观望，等待明确信号";
        } else {
            return "🔍 可考虑逢低建仓";
        }
    }

    /**
     * 构建报告
     */
    private String buildReport(String symbol, double price, double changePercent,
                                 String analysis, String recommendation) {
        return String.format(
            "[%s] %s - 价格: ¥%.2f, 涨跌幅: %+.2f%%\n分析: %s\n建议: %s",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            symbol, price, changePercent, analysis, recommendation
        );
    }

    /**
     * 发布分析报告
     *
     * @param report 报告内容
     */
    private void publishReport(String report) {
        System.out.println("   📄 发布分析报告:");
        for (String line : report.split("\n")) {
            System.out.println("      " + line);
        }
        System.out.println("   📤 发布渠道: " + publishChannel);
    }

    @Override
    public String getObserverName() {
        return "分析师-" + name;
    }
}
