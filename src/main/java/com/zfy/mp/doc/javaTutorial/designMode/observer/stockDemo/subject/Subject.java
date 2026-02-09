package com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.subject;

import com.zfy.mp.doc.javaTutorial.designMode.observer.stockDemo.observer.Observer;

/**
 * 被观察者接口（主题）
 * 定义被观察者的标准行为
 */
public interface Subject {
    /**
     * 注册一个观察者
     *
     * @param observer 观察者对象
     */
    void registerObserver(Observer observer);

    /**
     * 移除一个观察者
     *
     * @param observer 观察者对象
     */
    void removeObserver(Observer observer);

    /**
     * 通知所有注册的观察者
     */
    void notifyObservers();
}
