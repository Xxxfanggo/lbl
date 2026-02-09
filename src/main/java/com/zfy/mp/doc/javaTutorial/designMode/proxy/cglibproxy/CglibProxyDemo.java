package com.zfy.mp.doc.javaTutorial.designMode.proxy.cglibproxy;

/**
 * CGLIB 动态代理演示
 */
public class CglibProxyDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║         🎯 CGLIB 动态代理 - 事务管理示例                 ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 创建代理
        OrderService orderService = CglibProxyFactory.createProxy(
            OrderService.class, new TransactionInterceptor()
        );

        // 执行业务操作
        orderService.createOrder("ORD001", 99.99);
        orderService.getOrderStatus("ORD001");
        orderService.cancelOrder("ORD001");
    }
}
