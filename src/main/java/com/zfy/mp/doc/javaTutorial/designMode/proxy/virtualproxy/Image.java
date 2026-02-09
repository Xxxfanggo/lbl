package com.zfy.mp.doc.javaTutorial.designMode.proxy.virtualproxy;

/**
 * 图片接口 - 抽象主题
 */
public interface Image {
    /**
     * 显示图片
     */
    void display();

    /**
     * 获取图片信息
     *
     * @return 图片信息字符串
     */
    String getImageInfo();
}
