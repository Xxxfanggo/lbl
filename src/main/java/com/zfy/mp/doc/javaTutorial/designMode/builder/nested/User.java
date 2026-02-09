package com.zfy.mp.doc.javaTutorial.designMode.builder.nested;

import java.util.function.Consumer;

/**
 * 用户类 - 嵌套建造者模式
 */
public class User {
    private String name;
    private Integer age;
    private Address address;
    private Contact contact;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.address = builder.address;
        this.contact = builder.contact;
    }

    public void show() {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                       用户信息                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.println("  👤 姓名:   " + name);
        System.out.println("  🎂 年龄:   " + age);
        System.out.println("  📍 地址:   " + (address != null ? address : "未设置"));
        System.out.println("  📞 联系方式: " + (contact != null ? contact : "未设置"));
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    public static class Builder {
        // 必填参数
        private String name;
        private Integer age;

        // 可选的嵌套对象
        private Address address;
        private Contact contact;

        public Builder(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder contact(Contact contact) {
            this.contact = contact;
            return this;
        }

        /**
         * 嵌套的 Address Builder 方法
         */
        public Builder withAddress(Consumer<Address.Builder> consumer) {
            Address.Builder builder = new Address.Builder("默认城市", "默认街道");
            consumer.accept(builder);
            this.address = builder.build();
            return this;
        }

        /**
         * 嵌套的 Contact Builder 方法
         */
        public Builder withContact(Consumer<Contact.Builder> consumer) {
            Contact.Builder builder = new Contact.Builder();
            consumer.accept(builder);
            this.contact = builder.build();
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
