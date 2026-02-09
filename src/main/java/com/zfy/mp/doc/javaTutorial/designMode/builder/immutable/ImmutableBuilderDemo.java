package com.zfy.mp.doc.javaTutorial.designMode.builder.immutable;

/**
 * 不可变对象建造者模式演示
 */
public class ImmutableBuilderDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔨 不可变对象建造者 - 演示                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        // 构建开发环境配置
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 开发环境配置");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        ServerConfig devConfig = new ServerConfig.Builder("dev.example.com")
            .port(8080)
            .username("dev_user")
            .timeout(60000)
            .sslEnabled(false)
            .build();
        System.out.println("开发环境配置: " + devConfig);

        // 构建生产环境配置
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 生产环境配置");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        ServerConfig prodConfig = new ServerConfig.Builder("prod.example.com")
            .port(443)
            .username("admin")
            .password("******")
            .timeout(30000)
            .sslEnabled(true)
            .build();
        System.out.println("生产环境配置: " + prodConfig);
    }
}
