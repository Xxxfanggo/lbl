package com.zfy.mp.doc.javaTutorial.designMode.proxy.virtualproxy;

/**
 * 高清图片 - 真实主题
 * 实际加载和显示图片的类
 */
public class HighResolutionImage implements Image {

    // ── 基本属性 ──
    private String filename;        // 文件名
    private String title;           // 图片标题
    private int width;             // 宽度
    private int height;            // 高度
    private byte[] imageData;      // 图片数据
    private boolean isLoaded;      // 是否已加载

    // ── 构造方法 ──

    /**
     * 构造方法
     *
     * @param filename 文件名
     * @param title 图片标题
     * @param width 宽度
     * @param height 高度
     */
    public HighResolutionImage(String filename, String title, int width, int height) {
        this.filename = filename;
        this.title = title;
        this.width = width;
        this.height = height;
        this.isLoaded = false;
    }

    @Override
    public void display() {
        // 懒加载：第一次显示时才真正加载
        if (!isLoaded) {
            loadImage();
        }
        renderImage();
    }

    @Override
    public String getImageInfo() {
        return String.format("高清图片 [%s] - %dx%d - %s",
                filename, width, height, title);
    }

    /**
     * 加载图片数据
     * 模拟从磁盘或网络加载大文件的耗时操作
     */
    private void loadImage() {
        System.out.println("📦 正在从磁盘加载高清图片...");
        System.out.println("   文件: " + filename);

        // 模拟加载过程
        simulateLoadingProgress();

        // 生成模拟数据
        this.imageData = new byte[width * height * 4]; // RGBA格式
        this.isLoaded = true;

        System.out.println("✅ 图片加载完成！大小: " + (imageData.length / 1024) + " KB");
    }

    /**
     * 渲染图片
     */
    private void renderImage() {
        System.out.println("🖼️  正在渲染高清图片...");
        System.out.println("   标题: " + title);
        System.out.println("   分辨率: " + width + "x" + height);
        System.out.println("   数据大小: " + imageData.length + " 字节");
    }

    /**
     * 模拟加载进度
     */
    private void simulateLoadingProgress() {
        try {
            for (int i = 20; i <= 100; i += 20) {
                Thread.sleep(200);
                System.out.println("   加载进度: " + i + "%");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ── Getter 方法 ──

    public String getFilename() { return filename; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isLoaded() { return isLoaded; }
}
