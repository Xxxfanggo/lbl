package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: MacOSFactory.java
 * @工程名: bwcj-back
 * @包名: com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:48
 * @版本号: V2.4.0
 */
public class MacOSFactory implements UIFactory{
    @Override
    public Button createButton(String label) {
        return new MacOSButton(label);
    }
}
