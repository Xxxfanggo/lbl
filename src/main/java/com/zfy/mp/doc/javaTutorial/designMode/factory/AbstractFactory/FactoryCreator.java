package com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory;

/**
 * @文件名: FactoryCreater.java
 * @工程名: bwcj-back
 * @包名: com.zfy.mp.doc.javaTutorial.designMode.factory.AbstractFactory
 * @描述:
 * @创建人: zhongfangyu
 * @创建时间: 2026-02-03 16:49
 * @版本号: V2.4.0
 */
public class FactoryCreator {
    /**
     * 根据操作系统类型获取对应的 UI 工厂
     *
     * @param osType 操作系统类型（"windows" 或 "macos"）
     * @return 对应的 UI 工厂
     * @throws IllegalArgumentException 当操作系统类型不支持时抛出
     */
    public static UIFactory getFactory(String osType){
        switch (osType.toLowerCase())  {
            case "windows":
                return new WindowsFactory();
            case "macos":
                return new MacOSFactory();
            default:
                throw new IllegalArgumentException("不支持的操作系统类型: " + osType);

        }

    }

    public static UIFactory getFactory(){
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return new WindowsFactory();
        } else if (os.contains("mac")) {
            return new MacOSFactory();
        } else {
            System.out.println("警告：未识别的操作系统，使用默认风格");
            return new WindowsFactory();
        }
    }
}
