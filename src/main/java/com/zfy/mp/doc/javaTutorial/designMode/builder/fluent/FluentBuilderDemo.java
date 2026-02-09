package com.zfy.mp.doc.javaTutorial.designMode.builder.fluent;

/**
 * 链式建造者模式演示
 */
public class FluentBuilderDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔨 链式建造者模式 - 演示                      ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        // 构建基础配置电脑
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 基础配置电脑");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Computer basicComputer = new Computer.Builder(
            "Intel i5-12400", "16GB DDR4", "512GB SSD"
        ).build();
        basicComputer.showConfiguration();

        // 构建游戏电脑
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 游戏电脑");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Computer gamingComputer = new Computer.Builder(
            "Intel i7-12700K", "32GB DDR4", "1TB NVMe SSD"
        )
            .gpu("RTX 3080")
            .monitor("27寸 4K 显示器 144Hz")
            .keyboard("机械键盘 RGB")
            .mouse("电竞鼠标")
            .os("Windows 11 Pro")
            .hasWifi(true)
            .hasBluetooth(true)
            .hasCamera(true)
            .hasFingerprint(false)
            .build();
        gamingComputer.showConfiguration();

        // 构建办公电脑
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              3. 办公电脑");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Computer officeComputer = new Computer.Builder(
            "Intel i5-12400", "16GB DDR4", "256GB SSD"
        )
            .monitor("27寸显示器")
            .os("Windows 11")
            .hasWifi(true)
            .hasBluetooth(false)
            .hasCamera(true)
            .hasFingerprint(true)
            .build();
        officeComputer.showConfiguration();
    }
}
