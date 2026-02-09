### 什么是建造者模式？

建造者模式将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。它将复杂对象的创建过程分解为多个简单的步骤，用户只需指定复杂对象的类型和内容就可以构建对象。

#### 核心思想

分步骤构建复杂对象，灵活控制构建过程

---

#### 工作原理

```
                    建造者模式工作流程

   ┌─────────────────────────────────────────────────┐
   │              客户端代码                           │
   │                                                  │
   │  Director director = new Director(builder);    │
   │  Product product = director.construct();        │
   └─────────────────┬───────────────────────────────┘
                     │
                     ↓
         ┌───────────────────────────┐
         │      Director（指挥者）    │
         │   负责控制构建流程         │
         └─────────────┬─────────────┘
                       │
         ┌─────────────┴─────────────┐
         │                           │
         ↓                           ↓
  ┌──────────────┐          ┌──────────────┐
  │ Builder      │          │ Product      │
  │（建造者）    │          │（产品）      │
  │             │          │             │
  │ buildPart1()│          │ part1,part2 │
  │ buildPart2()│          │ part3,...   │
  │ getResult() │          └──────────────┘
  └──────────────┘
         │
         │ ConcreteBuilder
         │（具体建造者）
         ↓
  ┌──────────────┐
  │ ConcreteB1   │
  │ ConcreteB2   │
  └──────────────┘
```

---

#### 建造者模式角色

| 角色 | 说明 | 示例 |
|:----|:----|:----|
| Product（产品） | 最终要构建的复杂对象 | Computer |
| Builder（建造者） | 定义构建步骤的抽象接口 | ComputerBuilder |
| ConcreteBuilder（具体建造者） | 实现具体构建逻辑，并返回产品 | GamingComputerBuilder |
| Director（指挥者） | 控制构建流程，不负责具体细节 | ComputerDirector |

---

#### 完整代码示例

**场景：电脑配置构建器**

```java
import java.util.*;

// ==================== 1. 产品类（Computer） ====================

/**
 * 电脑产品类
 */
class Computer {
    private String cpu;           // CPU
    private String ram;           // 内存
    private String storage;       // 硬盘
    private String gpu;           // 显卡
    private String monitor;       // 显示器
    private String keyboard;      // 键盘
    private String mouse;         // 鼠标
    private String os;            // 操作系统
    private boolean hasWifi;      // 是否有WiFi
    private boolean hasBluetooth; // 是否有蓝牙
    private boolean hasCamera;    // 是否有摄像头
    private boolean hasFingerprint; // 是否有指纹识别

    // 私有构造方法，只能通过Builder创建
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.monitor = builder.monitor;
        this.keyboard = builder.keyboard;
        this.mouse = builder.mouse;
        this.os = builder.os;
        this.hasWifi = builder.hasWifi;
        this.hasBluetooth = builder.hasBluetooth;
        this.hasCamera = builder.hasCamera;
        this.hasFingerprint = builder.hasFingerprint;
    }

    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public String getMonitor() { return monitor; }
    public String getKeyboard() { return keyboard; }
    public String getMouse() { return mouse; }
    public String getOs() { return os; }
    public boolean hasWifi() { return hasWifi; }
    public boolean hasBluetooth() { return hasBluetooth; }
    public boolean hasCamera() { return hasCamera; }
    public boolean hasFingerprint() { return hasFingerprint; }

    /**
     * 显示电脑配置
     */
    public void showConfiguration() {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                   电脑配置清单                              ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.println("  ┌─────────────────────────────────────────────────────┐");
        System.out.println("  │  🖥️  CPU:       " + cpu + fillSpace(cpu, 30) + " │");
        System.out.println("  │  💾  内存:      " + ram + fillSpace(ram, 30) + " │");
        System.out.println("  │  💿  硬盘:      " + storage + fillSpace(storage, 30) + " │");
        System.out.println("  │  🎮  显卡:      " + gpu + fillSpace(gpu, 30) + " │");
        System.out.println("  │  🖥️  显示器:    " + monitor + fillSpace(monitor, 30) + " │");
        System.out.println("  │  ⌨️  键盘:      " + keyboard + fillSpace(keyboard, 30) + " │");
        System.out.println("  │  🖱️  鼠标:      " + mouse + fillSpace(mouse, 30) + " │");
        System.out.println("  │  💻  操作系统:  " + os + fillSpace(os, 30) + " │");
        System.out.println("  ├─────────────────────────────────────────────────────┤");
        System.out.println("  │  📶 WiFi:        " + (hasWifi ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  │  📡 蓝牙:        " + (hasBluetooth ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  │  📷 摄像头:      " + (hasCamera ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  │  👆 指纹识别:    " + (hasFingerprint ? "✅" : "❌") + fillSpace("", 33) + " │");
        System.out.println("  └─────────────────────────────────────────────────────┘");
    }

    private String fillSpace(String str, int total) {
        int spaceCount = total - str.length();
        return " ".repeat(Math.max(0, spaceCount));
    }

    /**
     * 建造者类（Builder）- 静态内部类
     */
    public static class Builder {
        // 必填参数
        private String cpu;
        private String ram;
        private String storage;

        // 可选参数（使用默认值）
        private String gpu = "集成显卡";
        private String monitor = "24寸显示器";
        private String keyboard = "标准键盘";
        private String mouse = "标准鼠标";
        private String os = "Windows 11";
        private boolean hasWifi = true;
        private boolean hasBluetooth = true;
        private boolean hasCamera = true;
        private boolean hasFingerprint = false;

        /**
         * 构造方法 - 必填参数
         */
        public Builder(String cpu, String ram, String storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }

        /**
         * 链式调用方法设置可选参数
         */
        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder monitor(String monitor) {
            this.monitor = monitor;
            return this;
        }

        public Builder keyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder mouse(String mouse) {
            this.mouse = mouse;
            return this;
        }

        public Builder os(String os) {
            this.os = os;
            return this;
        }

        public Builder hasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Builder hasBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        public Builder hasCamera(boolean hasCamera) {
            this.hasCamera = hasCamera;
            return this;
        }

        public Builder hasFingerprint(boolean hasFingerprint) {
            this.hasFingerprint = hasFingerprint;
            return this;
        }

        /**
         * 构建最终产品
         */
        public Computer build() {
            // 校验必填参数
            if (cpu == null || ram == null || storage == null) {
                throw new IllegalStateException("CPU、内存和硬盘为必填参数");
            }
            return new Computer(this);
        }
    }
}

// ==================== 2. 经典建造者模式实现 ====================

/**
 * 产品：套餐
 */
class Meal {
    private String burger;    // 汉堡
    private String drink;     // 饮料
    private String sideDish;  // 配菜
    private String toy;       // 玩具

    public void setBurger(String burger) { this.burger = burger; }
    public void setDrink(String drink) { this.drink = drink; }
    public void setSideDish(String sideDish) { this.sideDish = sideDish; }
    public void setToy(String toy) { this.toy = toy; }

    public void show() {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                       餐厅套餐                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");
        System.out.println("  🍔 汉堡:   " + burger);
        System.out.println("  🥤 饮料:   " + drink);
        System.out.println("  🍟 配菜:   " + sideDish);
        System.out.println("  🧸 玩具:   " + toy);
        System.out.println("═══════════════════════════════════════════════════════════");
    }
}

/**
 * 抽象建造者：套餐建造者
 */
abstract class MealBuilder {
    protected Meal meal = new Meal();

    public abstract void buildBurger();
    public abstract void buildDrink();
    public abstract void buildSideDish();
    public abstract void buildToy();

    public Meal getResult() {
        return meal;
    }
}

/**
 * 具体建造者：儿童套餐建造者
 */
class KidsMealBuilder extends MealBuilder {
    @Override
    public void buildBurger() {
        meal.setBurger("儿童小汉堡");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("苹果汁");
    }

    @Override
    public void buildSideDish() {
        meal.setSideDish("薯条(小份)");
    }

    @Override
    public void buildToy() {
        meal.setToy("卡通玩具");
    }
}

/**
 * 具体建造者：成人套餐建造者
 */
class AdultMealBuilder extends MealBuilder {
    @Override
    public void buildBurger() {
        meal.setBurger("牛肉汉堡");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("可乐(大杯)");
    }

    @Override
    public void buildSideDish() {
        meal.setSideDish("薯条(大份)");
    }

    @Override
    public void buildToy() {
        meal.setToy("无");
    }
}

/**
 * 指挥者：套餐组装员
 */
class MealDirector {
    private MealBuilder mealBuilder;

    public MealDirector(MealBuilder mealBuilder) {
        this.mealBuilder = mealBuilder;
    }

    public void setMealBuilder(MealBuilder mealBuilder) {
        this.mealBuilder = mealBuilder;
    }

    /**
     * 构建标准套餐
     */
    public Meal constructStandardMeal() {
        mealBuilder.buildBurger();
        mealBuilder.buildDrink();
        mealBuilder.buildSideDish();
        mealBuilder.buildToy();
        return mealBuilder.getResult();
    }

    /**
     * 构建简化套餐（没有玩具）
     */
    public Meal constructSimpleMeal() {
        mealBuilder.buildBurger();
        mealBuilder.buildDrink();
        mealBuilder.buildSideDish();
        return mealBuilder.getResult();
    }
}

// ==================== 3. 嵌套建造者模式（多层对象） ====================

/**
 * 地址类
 */
class Address {
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

/**
 * 联系人信息类
 */
class Contact {
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

/**
 * 用户类（包含嵌套的 Builder）
 */
class User {
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

// ==================== 4. 建造者模式变体：Immutable Builder ====================

/**
 * 不可变类配置
 */
final class ServerConfig {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final int timeout;
    private final boolean sslEnabled;

    private ServerConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.timeout = builder.timeout;
        this.sslEnabled = builder.sslEnabled;
    }

    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getUsername() { return username; }
    public int getTimeout() { return timeout; }
    public boolean isSslEnabled() { return sslEnabled; }

    @Override
    public String toString() {
        return String.format("ServerConfig{host='%s', port=%d, user='%s', timeout=%d, ssl=%s}",
            host, port, username, timeout, sslEnabled);
    }

    public static class Builder {
        // 必填参数
        private String host;

        // 可选参数（使用默认值）
        private int port = 8080;
        private String username = "guest";
        private String password = "";
        private int timeout = 30000;
        private boolean sslEnabled = false;

        public Builder(String host) {
            this.host = host;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder sslEnabled(boolean sslEnabled) {
            this.sslEnabled = sslEnabled;
            return this;
        }

        public ServerConfig build() {
            if (host == null || host.isEmpty()) {
                throw new IllegalStateException("Host cannot be null or empty");
            }
            if (port < 0 || port > 65535) {
                throw new IllegalStateException("Port must be between 0 and 65535");
            }
            return new ServerConfig(this);
        }
    }
}

// ==================== 客户端演示代码 ====================

/**
 * 建造者模式演示
 */
class BuilderPatternDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔨 建造者模式 - 完整演示                      ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝");

        // ── 1. 链式建造者演示 ──
        demonstrateFluentBuilder();

        // ── 2. 经典建造者模式演示 ──
        demonstrateClassicBuilder();

        // ── 3. 嵌套建造者演示 ──
        demonstrateNestedBuilder();

        // ── 4. 不可变建造者演示 ──
        demonstrateImmutableBuilder();
    }

    /**
     * 1. 链式建造者演示
     */
    private static void demonstrateFluentBuilder() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 链式建造者（推荐方式）");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 构建基础配置电脑
        Computer basicComputer = new Computer.Builder(
            "Intel i5-12400", "16GB DDR4", "512GB SSD"
        ).build();
        basicComputer.showConfiguration();

        // 构建游戏电脑
        Computer gamingComputer = new Computer.Builder(
            "Intel i7-12700K", "32GB DDR4", "1TB NVMe SSD"
        )
            .gpu("RTX 3080")
            .monitor("27寸 4K 显示器 144Hz")
            .keyboard("机械键盘 RGB")
            .mouse("电竞鼠标")
            .os("Windows 11 Pro")
            .hasWifi(true)
            .hasBluetooth(true)
            .hasCamera(true)
            .hasFingerprint(false)
            .build();
        gamingComputer.showConfiguration();

        // 构建办公电脑
        Computer officeComputer = new Computer.Builder(
            "Intel i5-12400", "16GB DDR4", "256GB SSD"
        )
            .monitor("27寸显示器")
            .os("Windows 11")
            .hasWifi(true)
            .hasBluetooth(false)
            .hasCamera(true)
            .hasFingerprint(true)
            .build();
        officeComputer.showConfiguration();
    }

    /**
     * 2. 经典建造者模式演示
     */
    private static void demonstrateClassicBuilder() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 经典建造者模式");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 创建指挥者
        MealDirector director = new MealDirector(null);

        // 构建儿童套餐
        MealBuilder kidsBuilder = new KidsMealBuilder();
        director.setMealBuilder(kidsBuilder);
        Meal kidsMeal = director.constructStandardMeal();
        kidsMeal.show();

        // 构建成人套餐
        MealBuilder adultBuilder = new AdultMealBuilder();
        director.setMealBuilder(adultBuilder);
        Meal adultMeal = director.constructStandardMeal();
        adultMeal.show();

        // 构建简化套餐
        Meal simpleMeal = director.constructSimpleMeal();
        simpleMeal.show();
    }

    /**
     * 3. 嵌套建造者演示
     */
    private static void demonstrateNestedBuilder() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              3. 嵌套建造者模式");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 使用嵌套 Builder 构建用户
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
        User simpleUser = new User.Builder("李四", 30).build();
        simpleUser.show();
    }

    /**
     * 4. 不可变建造者演示
     */
    private static void demonstrateImmutableBuilder() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              4. 不可变对象建造者");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 构建开发环境配置
        ServerConfig devConfig = new ServerConfig.Builder("dev.example.com")
            .port(8080)
            .username("dev_user")
            .timeout(60000)
            .sslEnabled(false)
            .build();
        System.out.println("开发环境配置: " + devConfig);

        // 构建生产环境配置
        ServerConfig prodConfig = new ServerConfig.Builder("prod.example.com")
            .port(443)
            .username("admin")
            .password("******")
            .timeout(30000)
            .sslEnabled(true)
            .build();
        System.out.println("生产环境配置: " + prodConfig);
    }
}
```

---

#### 实际应用：StringBuilder

JDK 中的 StringBuilder 是建造者模式的经典应用。

```java
/**
 * JDK StringBuilder 建造者模式演示
 */
class StringBuilderDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            📝 StringBuilder 建造者模式演示               ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 链式调用构建字符串
        String result = new StringBuilder()
            .append("Hello")
            .append(" ")
            .append("World")
            .append("!")
            .insert(5, " beautiful")
            .toString();

        System.out.println("结果: " + result);
        System.out.println("\n构建过程:");
        System.out.println("  1. append(\"Hello\")    → \"Hello\"");
        System.out.println("  2. append(\" \")         → \"Hello \"");
        System.out.println("  3. append(\"World\")    → \"Hello World\"");
        System.out.println("  4. append(\"!\")        → \"Hello World!\"");
        System.out.println("  5. insert(5, \" beautiful\") → \"Hello beautiful World!\"");
    }
}
```

---

#### 实际应用：Lombok @Builder

Lombok 提供的 @Builder 注解自动生成建造者代码。

```java
import lombok.Builder;
import lombok.ToString;

/**
 * 使用 Lombok @Builder 注解的示例
 * 编译器会自动生成建造者代码
 */
@Builder
@ToString
class Person {
    // 必填参数（可以指定 @NonNull 注解）
    private String name;
    private Integer age;

    // 可选参数
    private String email;
    private String phone;
    private String address;

    @Builder.Default
    private Boolean active = true;
}

/**
 * Lombok Builder 演示
 */
class LombokBuilderDemo {
    public static void main(String[] args) {
        // 使用自动生成的 Builder
        Person person = Person.builder()
            .name("张三")
            .age(25)
            .email("zhangsan@example.com")
            .phone("13800138000")
            .address("北京市")
            .active(true)
            .build();

        System.out.println(person);
        // 输出: Person(name=张三, age=25, email=zhangsan@example.com, phone=13800138000, address=北京市, active=true)
    }
}
```

---

#### 传统构造方法 vs 建造者模式

```java
/**
 * 对比：传统构造方法 vs 建造者模式
 */
class ConstructorVsBuilderDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║     🆚 传统构造方法 vs 建造者模式对比                   ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("❌ 方式一：重叠构造器（Telescoping Constructor）");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 问题：参数位置容易混淆，代码可读性差
        Computer1 c1 = new Computer1("i5", "16G", "512G");
        Computer1 c2 = new Computer1("i5", "16G", "512G", "RTX3060");
        Computer1 c3 = new Computer1("i5", "16G", "512G", "RTX3060", "27寸");
        Computer1 c4 = new Computer1("i5", "16G", "512G", "RTX3060", "27寸", "机械键盘");
        System.out.println("c4 = new Computer1(\"i5\", \"16G\", \"512G\", \"RTX3060\", \"27寸\", \"机械键盘\")");
        System.out.println("问题：参数含义不清晰，容易传错位置\n");

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("❌ 方式二：JavaBean Setter 方法");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 问题：对象处于不一致状态，无法保证不可变性
        Computer2 c = new Computer2();
        c.setCpu("i5");
        c.setRam("16G");
        c.setStorage("512G");
        c.setGpu("RTX3060");
        c.setMonitor("27寸");
        c.setKeyboard("机械键盘");
        System.out.println("对象创建过程中可能处于不一致状态\n");

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("✅ 方式三：建造者模式（推荐）");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // 优势：参数含义清晰，链式调用，易于扩展
        Computer ideal = new Computer.Builder("i5", "16G", "512G")
            .gpu("RTX3060")
            .monitor("27寸")
            .keyboard("机械键盘")
            .build();
        ideal.showConfiguration();
    }
}

// 重叠构造器方式
class Computer1 {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String monitor;
    private String keyboard;

    public Computer1(String cpu, String ram, String storage) {
        this(cpu, ram, storage, "集成显卡");
    }

    public Computer1(String cpu, String ram, String storage, String gpu) {
        this(cpu, ram, storage, gpu, "24寸");
    }

    public Computer1(String cpu, String ram, String storage, String gpu, String monitor) {
        this(cpu, ram, storage, gpu, monitor, "标准键盘");
    }

    public Computer1(String cpu, String ram, String storage, String gpu, String monitor, String keyboard) {
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.gpu = gpu;
        this.monitor = monitor;
        this.keyboard = keyboard;
    }
}

// JavaBean 方式
class Computer2 {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private String monitor;
    private String keyboard;

    public void setCpu(String cpu) { this.cpu = cpu; }
    public void setRam(String ram) { this.ram = ram; }
    public void setStorage(String storage) { this.storage = storage; }
    public void setGpu(String gpu) { this.gpu = gpu; }
    public void setMonitor(String monitor) { this.monitor = monitor; }
    public void setKeyboard(String keyboard) { this.keyboard = keyboard; }
}
```

---

#### 优缺点对比

| 类型 | 说明 |
|:----|:----|
| ✅ 优点 | • 将复杂对象的创建过程分离，代码结构清晰<br>• 支持链式调用，代码可读性强<br>• 可以控制构建步骤，保证对象完整性<br>• 支持创建不同表示的对象<br>• 适合创建有多个可选参数的对象 |
| ❌ 缺点 | • 代码量增加（每个产品都需要 Builder 类）<br>• 如果产品内部结构变化，Builder 也需要修改<br>• 对于简单对象可能过度设计 |

---

#### 适用场景

| 场景 | 说明 |
|:----|:----|
| 1 | 对象有多个可选参数，且参数数量较多 |
| 2 | 对象创建过程复杂，需要分步骤完成 |
| 3 | 相同的构建过程可以创建不同的表示 |
| 4 | 需要保证创建的对象是不可变的 |
| 5 | 需要创建多个相似但有细微差异的对象 |

---

#### 与其他设计模式对比

| 模式 | 目标 | 区别 |
|:----|:----|:----|
| 建造者模式 | 分步构建复杂对象 | 注重构建过程，产品内部结构可以变化 |
| 工厂模式 | 创建对象 | 注重创建对象，不关心构建细节 |
| 抽象工厂模式 | 创建产品族 | 创建一系列相关对象 |
| 原型模式 | 克隆对象 | 通过复制现有对象创建新对象 |

---

#### 建造者模式选择指南

```
                是否需要构建复杂对象？
                      │
            ┌─────────┴─────────┐
            │ 否                 │ 是
            ↓                   ↓
    使用工厂方法/简单工厂    是否需要分步构建？
                              │
                    ┌─────────┴─────────┐
                    │ 否                 │ 是
                    ↓                   ↓
            使用静态工厂方法       使用建造者模式
```

---

#### JDK 中的建造者应用

| 类 | 用途 |
|:----|:----|
| `StringBuilder` / `StringBuffer` | 构建字符串 |
| `DocumentBuilder` | 解析 XML 文档 |
| `Locale.Builder` | 构建 Locale 对象 |
| `UriBuilder` (JAX-RS) | 构建 URI |
| `Optional.empty()` | 创建空 Optional |

---

#### 总结

| 概念 | 说明 |
|:----|:----|
| 核心思想 | 将复杂对象的构建与表示分离 |
| 推荐实现 | 静态内部类 Builder + 链式调用 |
| 常见应用 | StringBuilder、Lombok @Builder |
| 与工厂模式区别 | 建造者注重构建过程，工厂注重创建结果 |
