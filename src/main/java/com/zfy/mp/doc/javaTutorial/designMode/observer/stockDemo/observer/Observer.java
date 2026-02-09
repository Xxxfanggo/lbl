package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer;

/**
 * 观察者接口
 * 定义所有观察者必须实现的方法
 */
public interface Observer {
    /**
     * 当被观察者发生变化时，调用此方法
     *
     * @param stockSymbol 股票代码
     * @param newPrice 新价格
     * @param changePercent 价格变化百分比
     */
    void update(String stockSymbol, double newPrice, double changePercent);

    /**
     * 获取观察者名称
     *
     * @return 观察者名称
     */
    String getObserverName();
}
