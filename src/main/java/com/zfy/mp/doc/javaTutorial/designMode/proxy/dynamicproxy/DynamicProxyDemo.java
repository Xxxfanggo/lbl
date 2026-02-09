package com.zfy.mp.doc.javaTutorial.designMode.proxy.dynamicproxy;

/**
 * JDK 动态代理演示
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║          🔧 JDK 动态代理 - 日志与权限控制                ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // ── 创建目标对象 ──
        UserService userService = new UserServiceImpl();

        // ── 创建日志代理 ──
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 日志代理演示");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        UserService logProxy = ProxyFactory.createLogProxy(userService, UserService.class);

        logProxy.addUser("张三", "zhangsan@example.com");
        logProxy.getUser("张三");
        logProxy.deleteUser("张三");

        // ── 创建权限代理 ──
        System.out.println("\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 权限代理演示");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        UserServiceImpl freshService = new UserServiceImpl();

        // 普通用户
        UserService userProxy = ProxyFactory.createPermissionProxy(
            freshService, UserService.class, "user");
        testPermissions(userProxy, "普通用户");

        // 编辑用户
        UserService editorProxy = ProxyFactory.createPermissionProxy(
            freshService, UserService.class, "editor");
        testPermissions(editorProxy, "编辑用户");

        // 管理员
        UserService adminProxy = ProxyFactory.createPermissionProxy(
            freshService, UserService.class, "admin");
        testPermissions(adminProxy, "管理员");
    }

    /**
     * 测试不同角色的权限
     */
    private static void testPermissions(UserService proxy, String role) {
        System.out.println("\n─────────────────────────────────────────────────────────");
        System.out.println("👤 角色测试: " + role);

        try {
            proxy.addUser("test", "test@example.com");
        } catch (SecurityException e) {
            System.out.println("❌ " + e.getMessage());
        }

        try {
            proxy.deleteUser("test");
        } catch (SecurityException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
