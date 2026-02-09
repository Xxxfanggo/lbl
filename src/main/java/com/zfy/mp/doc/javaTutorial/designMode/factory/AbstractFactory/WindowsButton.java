package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: WindowsButton.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:43
 * @版本号: V2.4.0
 */
public class WindowsButton implements Button{
    private String label;
    public WindowsButton(String label) {
        this.label = label;
    }
    @Override
    public void render() {
        System.out.println("渲染 Windows 风格按钮: " + label);
        System.out.println("  - 背景色: #F0F0F0");
        System.out.println("  - 边框: 1px solid #CCCCCC");
        System.out.println("  - 字体: Segoe UI, 12px");
    }
    @Override
    public void onClick() {
        System.out.println("Windows 按钮被点击: " + label);
    }
}
