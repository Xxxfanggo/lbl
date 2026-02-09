package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer;

/**
 * 交易员观察者
 * 当股票价格达到目标价格时执行交易
 */
public class TraderObserver implements Observer {

    private String name;           // 交易员姓名
    private double buyPrice;        // 目标买入价格
    private double sellPrice;       // 目标卖出价格
    private int quantity;           // 交易数量
    private boolean isHolding;      // 是否持有股票

    /**
     * 构造方法
     *
     * @param name 交易员姓名
     * @param buyPrice 目标买入价格
     * @param sellPrice 目标卖出价格
     * @param quantity 交易数量
     */
    public TraderObserver(String name, double buyPrice, double sellPrice, int quantity) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.isHolding = false;
    }

    @Override
    public void update(String stockSymbol, double newPrice, double changePercent) {
        System.out.println("👤 【" + name + "】收到通知");
        System.out.println("   当前价格: ¥" + String.format("%.2f", newPrice));

        // 判断交易操作
        if (isHolding) {
            // 持有中，判断是否卖出
            checkSellCondition(newPrice, stockSymbol);
        } else {
            // 未持有，判断是否买入
            checkBuyCondition(newPrice, stockSymbol);
        }
    }

    /**
     * 检查买入条件
     *
     * @param price 当前价格
     * @param symbol 股票代码
     */
    private void checkBuyCondition(double price, String symbol) {
        System.out.println("   目标买入价: ¥" + String.format("%.2f", buyPrice));

        if (price <= buyPrice) {
            // 价格低于目标价，执行买入
            executeBuyOrder(symbol, price);
        } else {
            System.out.println("   ❌ 价格未达买入条件，继续观望");
        }
    }

    /**
     * 检查卖出条件
     *
     * @param price 当前价格
     * @param symbol 股票代码
     */
    private void checkSellCondition(double price, String symbol) {
        System.out.println("   目标卖出价: ¥" + String.format("%.2f", sellPrice));
        System.out.println("   当前持仓: " + quantity + " 股");

        if (price >= sellPrice) {
            // 价格高于目标价，执行卖出
            executeSellOrder(symbol, price);
        } else {
            System.out.println("   ❌ 价格未达卖出条件，继续持有");
        }
    }

    /**
     * 执行买入订单
     *
     * @param symbol 股票代码
     * @param price 买入价格
     */
    private void executeBuyOrder(String symbol, double price) {
        double totalAmount = price * quantity;
        isHolding = true;

        System.out.println("   ✅ 执行买入订单！");
        System.out.println("   📋 订单详情:");
        System.out.println("      股票代码: " + symbol);
        System.out.println("      买入价格: ¥" + String.format("%.2f", price));
        System.out.println("      买入数量: " + quantity + " 股");
        System.out.println("      订单总额: ¥" + String.format("%.2f", totalAmount));
    }

    /**
     * 执行卖出订单
     *
     * @param symbol 股票代码
     * @param price 卖出价格
     */
    private void executeSellOrder(String symbol, double price) {
        double totalAmount = price * quantity;
        isHolding = false;

        System.out.println("   ✅ 执行卖出订单！");
        System.out.println("   📋 订单详情:");
        System.out.println("      股票代码: " + symbol);
        System.out.println("      卖出价格: ¥" + String.format("%.2f", price));
        System.out.println("      卖出数量: " + quantity + " 股");
        System.out.println("      订单总额: ¥" + String.format("%.2f", totalAmount));
    }

    @Override
    public String getObserverName() {
        return "交易员-" + name;
    }
}
