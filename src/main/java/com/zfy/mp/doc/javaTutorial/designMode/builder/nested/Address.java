package com.zfy.mp.doc.javaTutorial.designMode.builder.nested;

/**
 * 地址类 - 嵌套建造者模式
 */
public class Address {
    private String country;
    private String province;
    private String city;
    private String district;
    private String street;
    private String zipCode;

    private Address(Builder builder) {
        this.country = builder.country;
        this.province = builder.province;
        this.city = builder.city;
        this.district = builder.district;
        this.street = builder.street;
        this.zipCode = builder.zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s (%s)",
            country, province, city, district, street, zipCode);
    }

    public static class Builder {
        private String country;
        private String province;
        private String city;
        private String district;
        private String street;
        private String zipCode;

        public Builder(String city, String street) {
            this.city = city;
            this.street = street;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder district(String district) {
            this.district = district;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
