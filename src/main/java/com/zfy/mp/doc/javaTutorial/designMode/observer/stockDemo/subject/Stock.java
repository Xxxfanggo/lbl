package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.subject;

import com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 股票对象 - 具体被观察者
 * 当股票价格变化时，通知所有观察者
 */
public class Stock implements Subject {

    // ── 基本属性 ──
    private String symbol;     // 股票代码
    private String name;      // 股票名称
    private double price;     // 当前价格
    private double previousPrice;  // 前一次价格

    // ── 观察者管理 ──
    private List<Observer> observers;  // 观察者列表

    // ── 构造方法 ──

    /**
     * 构造方法
     *
     * @param symbol 股票代码
     * @param name 股票名称
     * @param price 初始价格
     */
    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.previousPrice = price;
        this.observers = new ArrayList<>();

        System.out.println("📈 股票创建: " + name + " (" + symbol + "), 初始价格: ¥" + price);
    }

    // ── Subject 接口实现 ──

    @Override
    public void registerObserver(Observer observer) {
        // 检查是否已存在，避免重复注册
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("✅ 观察者已注册: " + observer.getObserverName());
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("❌ 观察者已移除: " + observer.getObserverName());
        }
    }

    @Override
    public void notifyObservers() {
        // 计算价格变化百分比
        double changePercent = calculateChangePercent();

        System.out.println("\n📢 正在通知 " + observers.size() + " 位观察者...\n");

        // 遍历所有观察者，调用它们的 update 方法
        for (Observer observer : observers) {
            observer.update(symbol, price, changePercent);
        }
    }

    // ── 业务方法 ──

    /**
     * 设置新价格
     * 价格变化时会自动通知所有观察者
     *
     * @param newPrice 新价格
     */
    public void setPrice(double newPrice) {
        if (newPrice < 0) {
            System.out.println("⚠️ 价格不能为负数");
            return;
        }

        this.previousPrice = this.price;
        this.price = newPrice;

        // 打印价格变化信息
        printPriceChange();

        // 通知所有观察者
        notifyObservers();
    }

    /**
     * 计算价格变化百分比
     *
     * @return 变化百分比
     */
    private double calculateChangePercent() {
        if (previousPrice == 0) {
            return 0;
        }
        return ((price - previousPrice) / previousPrice) * 100;
    }

    /**
     * 打印价格变化信息
     */
    private void printPriceChange() {
        double change = price - previousPrice;
        double changePercent = calculateChangePercent();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("         📊 " + name + " (" + symbol + ") 价格更新");
        System.out.println("=".repeat(60));
        System.out.println("  前价格: ¥" + String.format("%.2f", previousPrice));
        System.out.println("  新价格: ¥" + String.format("%.2f", price));
        System.out.println("  变化量: " + (change >= 0 ? "+" : "") + String.format("%.2f", change));
        System.out.println("  涨跌幅: " + (changePercent >= 0 ? "📈 +" : "📉 ") + String.format("%.2f%%", changePercent));
        System.out.println("=".repeat(60));
    }

    // ── Getter 方法 ──

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getObserverCount() { return observers.size(); }
}
