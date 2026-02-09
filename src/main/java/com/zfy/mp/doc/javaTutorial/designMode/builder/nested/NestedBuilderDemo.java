package com.zfy.mp.doc.javaTutorial.designMode.builder.nested;

/**
 * 嵌套建造者模式演示
 */
public class NestedBuilderDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔨 嵌套建造者模式 - 演示                      ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        // 使用嵌套 Builder 构建完整信息的用户
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 完整信息用户");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        User user = new User.Builder("张三", 25)
            .withAddress(addr -> addr
                .country("中国")
                .province("北京市")
                .city("北京市")
                .district("朝阳区")
                .street("建国路88号")
                .zipCode("100025")
            )
            .withContact(contact -> contact
                .email("zhangsan@example.com")
                .phone("13800138000")
                .wechat("zhangsan_wx")
            )
            .build();
        user.show();

        // 最简方式
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 最简信息用户");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        User simpleUser = new User.Builder("李四", 30).build();
        simpleUser.show();
    }
}
