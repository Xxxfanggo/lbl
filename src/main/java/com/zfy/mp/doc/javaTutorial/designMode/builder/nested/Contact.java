package com.zfy.mp.doc.javaTutorial.designMode.builder.nested;

/**
 * 联系人信息类 - 嵌套建造者模式
 */
public class Contact {
    private String email;
    private String phone;
    private String wechat;

    private Contact(Builder builder) {
        this.email = builder.email;
        this.phone = builder.phone;
        this.wechat = builder.wechat;
    }

    @Override
    public String toString() {
        return String.format("Email: %s, Phone: %s, WeChat: %s",
            email, phone, wechat);
    }

    public static class Builder {
        private String email;
        private String phone;
        private String wechat;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder wechat(String wechat) {
            this.wechat = wechat;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }
}
