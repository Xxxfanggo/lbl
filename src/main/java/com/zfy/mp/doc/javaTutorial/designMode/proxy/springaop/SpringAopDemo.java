package com.zfy.mp.doc.javaTutorial.designMode.proxy.springaop;


import com.zfy.mp.doc.javaTutorial.designMode.proxy.springaop.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring AOP 演示
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringAopDemo {

    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║         🎯 Spring AOP - 面向切面编程演示                ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 启动Spring应用上下文
        ConfigurableApplicationContext context = SpringApplication.run(SpringAopDemo.class, args);

        // 从容器中获取ProductService bean（已经是被代理的对象）
        ProductService productService = context.getBean(ProductService.class);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              测试产品服务方法");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // 调用产品服务方法
        System.out.println("\n─────────────────────────────────────────────────────────");
        System.out.println("📦 操作：创建产品");
        productService.createProduct("P001", "笔记本电脑", 5999.99);

        System.out.println("\n─────────────────────────────────────────────────────────");
        System.out.println("💰 操作：更新价格");
        productService.updatePrice("P001", 5499.99);

        // 关闭应用上下文
        context.close();
    }
}
