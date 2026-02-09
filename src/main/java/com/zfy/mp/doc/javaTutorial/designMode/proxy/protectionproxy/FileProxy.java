package com.zfy.mp.doc.javaTutorial.designMode.proxy.protectionproxy;

/**
 * 文件代理 - 保护代理
 * 控制对敏感文件的访问权限
 */
public class FileProxy implements SensitiveFile {
    private RealFile realFile;
    private String filename;

    public FileProxy(String filename, String content) {
        this.filename = filename;
        // 延迟创建真实文件
        this.realFile = null;
    }

    @Override
    public void read(User user) {
        if (!checkPermission(user, Permission.READ)) {
            System.out.println("❌ 拒绝访问: " + user.getUsername() + " 无权限读取文件 " + filename);
            return;
        }
        ensureRealFileExists();
        realFile.read(user);
    }

    @Override
    public void write(User user, String content) {
        if (!checkPermission(user, Permission.WRITE)) {
            System.out.println("❌ 拒绝访问: " + user.getUsername() + " 无权限写入文件 " + filename);
            return;
        }
        ensureRealFileExists();
        realFile.write(user, content);
    }

    @Override
    public void delete(User user) {
        if (!checkPermission(user, Permission.ADMIN)) {
            System.out.println("❌ 拒绝访问: " + user.getUsername() + " 无权限删除文件 " + filename);
            return;
        }
        ensureRealFileExists();
        realFile.delete(user);
    }

    /**
     * 检查用户权限
     */
    private boolean checkPermission(User user, Permission required) {
        switch (required) {
            case READ:
                return user.getPermission() == Permission.READ ||
                       user.getPermission() == Permission.WRITE ||
                       user.getPermission() == Permission.ADMIN;
            case WRITE:
                return user.getPermission() == Permission.WRITE ||
                       user.getPermission() == Permission.ADMIN;
            case ADMIN:
                return user.getPermission() == Permission.ADMIN;
            default:
                return false;
        }
    }

    /**
     * 确保真实文件对象存在
     */
    private void ensureRealFileExists() {
        if (realFile == null) {
            realFile = new RealFile(filename, "初始内容");
        }
    }
}
