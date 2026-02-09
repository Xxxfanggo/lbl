package com.zfy.mp.doc.javaTutorial.designMode.proxy.cglibproxy;

/**
 * 订单服务 - 没有实现任何接口
 */
public class OrderService {
    public void createOrder(String orderId, double amount) {
        System.out.println("✅ 订单已创建: " + orderId + ", 金额: ¥" + amount);
    }

    public void cancelOrder(String orderId) {
        System.out.println("🗑️  订单已取消: " + orderId);
    }

    public String getOrderStatus(String orderId) {
        return "PAID";
    }
}
