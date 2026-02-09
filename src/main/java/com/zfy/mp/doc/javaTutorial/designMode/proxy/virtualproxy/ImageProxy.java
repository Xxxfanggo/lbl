package com.zfy.mp.doc.javaTutorial.designMode.proxy.virtualproxy;

/**
 * 图片代理 - 虚拟代理
 * 延迟加载图片，只在真正需要显示时才加载
 */
public class ImageProxy implements Image {

    // ── 基本属性 ──
    private String filename;                // 文件名
    private String title;                   // 图片标题
    private int width;                     // 宽度
    private int height;                    // 高度

    // ── 代理控制 ──
    private HighResolutionImage realImage;  // 真实图片对象（延迟创建）
    private boolean isLoaded;              // 是否已请求加载

    // ── 缩略图缓存 ──
    private String thumbnail;               // 缩略图数据

    // ── 构造方法 ──

    /**
     * 构造方法
     *
     * @param filename 文件名
     * @param title 图片标题
     * @param width 宽度
     * @param height 高度
     */
    public ImageProxy(String filename, String title, int width, int height) {
        this.filename = filename;
        this.title = title;
        this.width = width;
        this.height = height;
        this.isLoaded = false;

        // 创建缩略图（轻量级操作）
        generateThumbnail();
    }

    @Override
    public void display() {
        if (!isLoaded) {
            // 首次显示：先展示缩略图
            displayThumbnail();

            // 询问用户是否加载高清图
            System.out.println("\n💡 提示：按 Enter 加载高清图片，或跳过...");
            // 实际应用中可能是用户点击"查看高清"按钮

            // 延迟创建真实图片对象
            System.out.println("\n⏳ 创建真实图片对象...");
            realImage = new HighResolutionImage(filename, title, width, height);
            isLoaded = true;
        }

        // 委托给真实对象
        realImage.display();
    }

    @Override
    public String getImageInfo() {
        return String.format("图片代理 [%s] - %dx%d - %s (未加载)",
                filename, width, height, title);
    }

    /**
     * 显示缩略图
     */
    private void displayThumbnail() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          🖼️  显示缩略图");
        System.out.println("=".repeat(50));
        System.out.println("   标题: " + title);
        System.out.println("   文件: " + filename);
        System.out.println("   缩略图数据: " + thumbnail);
        System.out.println("   原始尺寸: " + width + "x" + height);
        System.out.println("=".repeat(50));
    }

    /**
     * 生成缩略图
     * 这是一个轻量级操作，快速完成
     */
    private void generateThumbnail() {
        // 模拟生成缩略图
        this.thumbnail = "[缩略图数据: 64x64]";
        System.out.println("🖼️  图片代理创建: " + title + " (" + filename + ")");
        System.out.println("   ✅ 缩略图已生成（轻量级）");
        System.out.println("   ⏳ 高清图将在需要时加载");
    }

    // ── Getter 方法 ──

    public String getFilename() { return filename; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isRealImageLoaded() { return isLoaded; }
}
