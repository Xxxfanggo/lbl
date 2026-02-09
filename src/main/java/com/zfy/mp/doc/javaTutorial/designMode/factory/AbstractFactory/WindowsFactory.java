package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: WindowsFactory.java
 * @工程名: bwcj-back
 * @包名: com.zfy.bwcj.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:47
 * @版本号: V2.4.0
 */
public class WindowsFactory implements UIFactory{
    @Override
    public Button createButton(String label) {
        return new WindowsButton(label);
    }

}
