package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: MacOSButton.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:44
 * @版本号: V2.4.0
 */
public class MacOSButton implements Button{
    private String label;

    public MacOSButton(String label) {
        this.label = label;
    }

    @Override
    public void render() {
        System.out.println("渲染 MacOS 风格按钮: " + label);
        System.out.println("  - 背景色: #007AFF（系统蓝）");
        System.out.println("  - 圆角: 6px");
        System.out.println("  - 字体: San Francisco, 13px");
    }

    @Override
    public void onClick() {
        System.out.println("MacOS 按钮被点击: " + label);
        // MacOS 特有的点击效果
    }


}
