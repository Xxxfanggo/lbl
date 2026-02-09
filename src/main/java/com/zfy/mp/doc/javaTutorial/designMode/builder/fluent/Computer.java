package com.zfy.mp.doc.javaTutorial.designMode.builder.fluent;

/**
 * 电脑产品类 - 链式建造者模式
 */
public class Computer {
    private String cpu;           // CPU
    private String ram;           // 内存
    private String storage;       // 硬盘
    private String gpu;           // 显卡
    private String monitor;       // 显示器
    private String keyboard;      // 键盘
    private String mouse;         // 鼠标
    private String os;            // 操作系统
    private boolean hasWifi;      // 是否有WiFi
    private boolean hasBluetooth; // 是否有蓝牙
    private boolean hasCamera;    // 是否有摄像头
    private boolean hasFingerprint; // 是否有指纹识别

    // 私有构造方法，只能通过Builder创建
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.monitor = builder.monitor;
        this.keyboard = builder.keyboard;
        this.mouse = builder.mouse;
        this.os = builder.os;
        this.hasWifi = builder.hasWifi;
        this.hasBluetooth = builder.hasBluetooth;
        this.hasCamera = builder.hasCamera;
        this.hasFingerprint = builder.hasFingerprint;
    }

    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public String getMonitor() { return monitor; }
    public String getKeyboard() { return keyboard; }
    public String getMouse() { return mouse; }
    public String getOs() { return os; }
    public boolean hasWifi() { return hasWifi; }
    public boolean hasBluetooth() { return hasBluetooth; }
    public boolean hasCamera() { return hasCamera; }
    public boolean hasFingerprint() { return hasFingerprint; }

    /**
     * 显示电脑配置
     */
    public void showConfiguration() {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                   电脑配置清单                              ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.println("  ┌─────────────────────────────────────────────────────┐");
        System.out.println("  │  🖥️  CPU:       " + cpu + fillSpace(cpu, 30) + " │");
        System.out.println("  │  💾  内存:      " + ram + fillSpace(ram, 30) + " │");
        System.out.println("  │  💿  硬盘:      " + storage + fillSpace(storage, 30) + " │");
        System.out.println("  │  🎮  显卡:      " + gpu + fillSpace(gpu, 30) + " │");
        System.out.println("  │  🖥️  显示器:    " + monitor + fillSpace(monitor, 30) + " │");
        System.out.println("  │  ⌨️  键盘:      " + keyboard + fillSpace(keyboard, 30) + " │");
        System.out.println("  │  🖱️  鼠标:      " + mouse + fillSpace(mouse, 30) + " │");
        System.out.println("  │  💻  操作系统:  " + os + fillSpace(os, 30) + " │");
        System.out.println("  ├─────────────────────────────────────────────────────┤");
        System.out.println("  │  📶 WiFi:        " + (hasWifi ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  │  📡 蓝牙:        " + (hasBluetooth ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  │  📷 摄像头:      " + (hasCamera ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  │  👆 指纹识别:    " + (hasFingerprint ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  └─────────────────────────────────────────────────────┘");
    }

    private String fillSpace(String str, int total) {
        int spaceCount = total - str.length();
        return " ".repeat(Math.max(0, spaceCount));
    }

    /**
     * 建造者类（Builder）- 静态内部类
     */
    public static class Builder {
        // 必填参数
        private String cpu;
        private String ram;
        private String storage;

        // 可选参数（使用默认值）
        private String gpu = "集成显卡";
        private String monitor = "24寸显示器";
        private String keyboard = "标准键盘";
        private String mouse = "标准鼠标";
        private String os = "Windows 11";
        private boolean hasWifi = true;
        private boolean hasBluetooth = true;
        private boolean hasCamera = true;
        private boolean hasFingerprint = false;

        /**
         * 构造方法 - 必填参数
         */
        public Builder(String cpu, String ram, String storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }

        /**
         * 链式调用方法设置可选参数
         */
        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder monitor(String monitor) {
            this.monitor = monitor;
            return this;
        }

        public Builder keyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder mouse(String mouse) {
            this.mouse = mouse;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public Builder hasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Builder hasBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        public Builder hasCamera(boolean hasCamera) {
            this.hasCamera = hasCamera;
            return this;
        }

        public Builder hasFingerprint(boolean hasFingerprint) {
            this.hasFingerprint = hasFingerprint;
            return this;
        }

        /**
         * 构建最终产品
         */
        public Computer build() {
            // 校验必填参数
            if (cpu == null || ram == null || storage == null) {
                throw new IllegalStateException("CPU、内存和硬盘为必填参数");
            }
            return new Computer(this);
        }
    }
}
