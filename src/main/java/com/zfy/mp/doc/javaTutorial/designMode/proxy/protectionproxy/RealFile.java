package com.zfy.mp.doc.javaTutorial.designMode.proxy.protectionproxy;

/**
 * 真实文件 - 真实主题
 */
public class RealFile implements SensitiveFile {
    private String filename;
    private String content;

    public RealFile(String filename, String content) {
        this.filename = filename;
        this.content = content;
    }

    @Override
    public void read(User user) {
        System.out.println("📄 读取文件: " + filename);
        System.out.println("   内容: " + content);
    }

    @Override
    public void write(User user, String content) {
        this.content = content;
        System.out.println("✅ 文件已更新: " + filename);
    }

    @Override
    public void delete(User user) {
        this.content = "";
        System.out.println("🗑️  文件已删除: " + filename);
    }
}
