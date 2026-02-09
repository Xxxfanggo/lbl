package com.zfy.mp.doc.javaTutorial.designMode.proxy.protectionproxy;

/**
 * 保护代理演示
 */
public class ProtectionProxyDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔒 代理模式 - 保护代理演示                 ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              保护代理 - 权限控制");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // 创建文件代理
        SensitiveFile sensitiveFile = new FileProxy("config.ini", "数据库配置信息");

        // 创建不同权限的用户
        User reader = new User("普通用户", Permission.READ);
        User writer = new User("编辑用户", Permission.WRITE);
        User admin = new User("管理员", Permission.ADMIN);

        System.out.println("👥 用户列表:");
        System.out.println("   1. " + reader.getUsername() + " - 权限: " + reader.getPermission());
        System.out.println("   2. " + writer.getUsername() + " - 权限: " + writer.getPermission());
        System.out.println("   3. " + admin.getUsername() + " - 权限: " + admin.getPermission());
        System.out.println();

        // 测试不同用户的操作
        testUserAction(sensitiveFile, reader, "读取");
        testUserAction(sensitiveFile, reader, "写入");
        testUserAction(sensitiveFile, reader, "删除");

        testUserAction(sensitiveFile, writer, "写入");
        testUserAction(sensitiveFile, writer, "删除");

        testUserAction(sensitiveFile, admin, "删除");
    }

    /**
     * 测试用户操作
     */
    private static void testUserAction(SensitiveFile file, User user, String action) {
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("👤 用户: " + user.getUsername() + " 尝试操作: " + action);

        switch (action) {
            case "读取":
                file.read(user);
                break;
            case "写入":
                file.write(user, "新内容");
                break;
            case "删除":
                file.delete(user);
                break;
        }
    }
}
