### 什么是单例模式？

单例模式确保一个类只有一个实例，并提供一个全局访问点。无论在程序的任何地方获取该类的实例，得到的都是同一个对象。

#### 核心思想

控制实例数量，确保全局唯一

---

#### 工作原理

```
                    单例模式工作流程

   ┌─────────────────────────────────────────────────┐
   │              客户端代码                           │
   │                                                  │
   │  Singleton instance1 = Singleton.getInstance(); │
   │  Singleton instance2 = Singleton.getInstance(); │
   └─────────────────┬───────────────────────────────┘
                     │ 调用静态方法
                     ↓
         ┌───────────────────────────┐
         │    Singleton 类          │
         └─────────────┬─────────────┘
                       │
         ┌─────────────┴─────────────┐
         │                           │
         ↓                           ↓
  ┌──────────────┐          ┌──────────────┐
  │ instance ==  │          │ instance !=  │
  │   null ?     │          │   null ?     │
  └──────┬───────┘          └──────┬───────┘
         │                           │
         ↓                           │
  ┌──────────────┐                  │
  │ 创建新实例   │                  │
  │ instance =   │                  │
  │ new Singleton│                  │
  └──────┬───────┘                  │
         │                           │
         └─────────────┬─────────────┘
                       ↓
         ┌───────────────────────────┐
         │     返回 instance         │
         └───────────────────────────┘
```

---

#### 单例实现方式对比

| 实现方式 | 线程安全 | 延迟加载 | 实现难度 | 推荐度 |
|:--------|:-------:|:-------:|:-------:|:-----:|
| 饿汉式 | ✅ | ❌ | ⭐ | ⭐⭐⭐ |
| 懒汉式（线程不安全） | ❌ | ✅ | ⭐ | ⭐ |
| 懒汉式（同步方法） | ✅ | ✅ | ⭐⭐ | ⭐⭐ |
| 双重检查锁 | ✅ | ✅ | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| 静态内部类 | ✅ | ✅ | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| 枚举 | ✅ | ❌ | ⭐⭐ | ⭐⭐⭐⭐⭐ |

---

#### 完整代码示例

**场景：数据库连接池管理器**

```java
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// ==================== 1. 饿汉式 ====================

/**
 * 饿汉式单例
 * 在类加载时就创建实例，线程安全
 *
 * 优点：实现简单，线程安全
 * 缺点：无论是否使用都会创建实例，可能造成资源浪费
 */
class EagerSingleton {
    // ── 静态实例，类加载时初始化 ──
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // ── 私有构造方法，防止外部创建 ──
    private EagerSingleton() {
        System.out.println("🔄 饿汉式单例：实例已创建");
    }

    // ── 公共静态访问方法 ──
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    // ── 业务方法 ──
    public void doSomething() {
        System.out.println("📌 饿汉式单例执行业务逻辑");
    }
}

// ==================== 2. 懒汉式（线程不安全） ====================

/**
 * 懒汉式单例（线程不安全）
 * 第一次调用时才创建实例
 *
 * 优点：延迟加载，节省资源
 * 缺点：多线程环境下不安全
 */
class LazySingletonUnsafe {
    // ── 静态实例，初始为 null ──
    private static LazySingletonUnsafe instance;

    // ── 私有构造方法 ──
    private LazySingletonUnsafe() {
        System.out.println("🔄 懒汉式单例（不安全）：实例已创建");
    }

    // ── 公共静态访问方法（线程不安全）──
    public static LazySingletonUnsafe getInstance() {
        if (instance == null) {
            instance = new LazySingletonUnsafe();
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("📌 懒汉式单例（不安全）执行业务逻辑");
    }
}

// ==================== 3. 懒汉式（同步方法） ====================

/**
 * 懒汉式单例（同步方法）
 * 通过 synchronized 保证线程安全
 *
 * 优点：线程安全，延迟加载
 * 缺点：每次获取实例都要加锁，性能较差
 */
class LazySingletonSync {
    private static LazySingletonSync instance;

    private LazySingletonSync() {
        System.out.println("🔄 懒汉式单例（同步方法）：实例已创建");
    }

    // ── 同步方法，保证线程安全 ──
    public static synchronized LazySingletonSync getInstance() {
        if (instance == null) {
            instance = new LazySingletonSync();
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("📌 懒汉式单例（同步方法）执行业务逻辑");
    }
}

// ==================== 4. 双重检查锁（Double-Check Locking） ====================

/**
 * 双重检查锁单例
 * 推荐的懒加载实现方式
 *
 * 优点：线程安全，延迟加载，性能优秀
 * 原理：只在第一次创建时加锁，后续直接返回
 */
class DoubleCheckLockSingleton {
    // ── volatile 保证可见性和禁止指令重排序 ──
    private static volatile DoubleCheckLockSingleton instance;

    private DoubleCheckLockSingleton() {
        System.out.println("🔄 双重检查锁单例：实例已创建");
    }

    // ── 双重检查 ──
    public static DoubleCheckLockSingleton getInstance() {
        // 第一次检查：不加锁，快速判断
        if (instance == null) {
            synchronized (DoubleCheckLockSingleton.class) {
                // 第二次检查：加锁后再次判断
                if (instance == null) {
                    instance = new DoubleCheckLockSingleton();
                }
            }
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("📌 双重检查锁单例执行业务逻辑");
    }
}

// ==================== 5. 静态内部类 ====================

/**
 * 静态内部类单例
 * 利用类加载机制保证线程安全和延迟加载
 *
 * 优点：线程安全，延迟加载，无锁，性能最优
 * 原理：内部类在外部类调用时才加载
 */
class StaticInnerClassSingleton {

    // ── 私有构造方法 ──
    private StaticInnerClassSingleton() {
        System.out.println("🔄 静态内部类单例：实例已创建");
    }

    // ── 静态内部类，持有单例实例 ──
    private static class Holder {
        // 静态成员变量在类加载时初始化
        private static final StaticInnerClassSingleton INSTANCE =
            new StaticInnerClassSingleton();
    }

    // ── 公共访问方法 ──
    public static StaticInnerClassSingleton getInstance() {
        return Holder.INSTANCE;
    }

    public void doSomething() {
        System.out.println("📌 静态内部类单例执行业务逻辑");
    }
}

// ==================== 6. 枚举单例 ====================

/**
 * 枚举单例
 * 利用枚举特性实现单例
 *
 * 优点：线程安全，自动支持序列化，防止反射攻击
 * 缺点：不支持延迟加载
 */
enum EnumSingleton {
    // 枚举实例本身就是单例
    INSTANCE;

    // ── 业务方法 ──
    public void doSomething() {
        System.out.println("📌 枚举单例执行业务逻辑");
    }

    // ── 其他方法 ──
    public String getInfo() {
        return "枚举单例信息";
    }

    // 枚举的构造方法自动被调用
    static {
        System.out.println("🔄 枚举单例：实例已创建");
    }
}

// ==================== 7. 带参数的单例工厂 ====================

/**
 * 数据库配置
 */
class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private int maxConnections;

    public DatabaseConfig(String url, String username, String password, int maxConnections) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.maxConnections = maxConnections;
    }

    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public int getMaxConnections() { return maxConnections; }
}

/**
 * 数据库连接池管理器 - 带参数的单例
 */
class ConnectionPoolManager {
    private static ConnectionPoolManager instance;

    private DatabaseConfig config;
    private List<String> activeConnections;
    private AtomicInteger connectionCount;

    private ConnectionPoolManager(DatabaseConfig config) {
        this.config = config;
        this.activeConnections = new ArrayList<>();
        this.connectionCount = new AtomicInteger(0);
        System.out.println("🔄 连接池管理器初始化");
        System.out.println("   URL: " + config.getUrl());
        System.out.println("   最大连接数: " + config.getMaxConnections());
    }

    /**
     * 获取单例实例（带初始化参数）
     */
    public static synchronized ConnectionPoolManager getInstance(DatabaseConfig config) {
        if (instance == null && config != null) {
            instance = new ConnectionPoolManager(config);
        }
        return instance;
    }

    /**
     * 获取单例实例（不带参数）
     */
    public static ConnectionPoolManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("连接池未初始化，请先调用 getInstance(DatabaseConfig)");
        }
        return instance;
    }

    /**
     * 获取连接
     */
    public String getConnection() {
        if (activeConnections.size() >= config.getMaxConnections()) {
            throw new RuntimeException("连接池已满");
        }

        String connectionId = "conn-" + connectionCount.incrementAndGet();
        activeConnections.add(connectionId);

        System.out.println("🔌 获取连接: " + connectionId);
        System.out.println("   活跃连接数: " + activeConnections.size() + "/" + config.getMaxConnections());

        return connectionId;
    }

    /**
     * 释放连接
     */
    public void releaseConnection(String connectionId) {
        activeConnections.remove(connectionId);
        System.out.println("✅ 释放连接: " + connectionId);
        System.out.println("   活跃连接数: " + activeConnections.size() + "/" + config.getMaxConnections());
    }

    /**
     * 获取连接池状态
     */
    public void printPoolStatus() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           连接池状态");
        System.out.println("=".repeat(50));
        System.out.println("数据库: " + config.getUrl());
        System.out.println("用户名: " + config.getUsername());
        System.out.println("活跃连接数: " + activeConnections.size());
        System.out.println("最大连接数: " + config.getMaxConnections());
        System.out.println("总创建连接数: " + connectionCount.get());
        System.out.println("=".repeat(50) + "\n");
    }
}

// ==================== 8. 注册式单例（容器管理） ====================

/**
 * 单例容器
 * 管理多个单例实例
 */
class SingletonRegistry {
    private static final Map<String, Object> registry = new ConcurrentHashMap<>();

    /**
     * 注册单例
     */
    public static void register(String key, Object instance) {
        registry.put(key, instance);
        System.out.println("📋 注册单例: " + key);
    }

    /**
     * 获取单例
     */
    public static <T> T getInstance(String key, Class<T> type) {
        Object instance = registry.get(key);
        if (instance != null && type.isInstance(instance)) {
            return type.cast(instance);
        }
        return null;
    }

    /**
     * 获取所有注册的名称
     */
    public static Set<String> getAllKeys() {
        return new HashSet<>(registry.keySet());
    }
}

// ==================== 客户端代码 ====================

/**
 * 单例模式演示
 */
class SingletonPatternDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🔒 单例模式 - 实现方式对比                   ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // ── 1. 饿汉式演示 ──
        demonstrateEagerSingleton();

        // ── 2. 懒汉式演示 ──
        demonstrateLazySingleton();

        // ── 3. 双重检查锁演示 ──
        demonstrateDoubleCheckLock();

        // ── 4. 静态内部类演示 ──
        demonstrateStaticInnerClass();

        // ── 5. 枚举单例演示 ──
        demonstrateEnumSingleton();

        // ── 6. 带参数单例演示 ──
        demonstrateConnectionPool();

        // ── 7. 注册式单例演示 ──
        demonstrateSingletonRegistry();
    }

    /**
     * 演示饿汉式
     */
    private static void demonstrateEagerSingleton() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 饿汉式单例");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        EagerSingleton instance1 = EagerSingleton.getInstance();
        EagerSingleton instance2 = EagerSingleton.getInstance();

        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println("instance1.hashCode(): " + instance1.hashCode());
        System.out.println("instance2.hashCode(): " + instance2.hashCode());
        System.out.println();
    }

    /**
     * 演示懒汉式
     */
    private static void demonstrateLazySingleton() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 懒汉式单例");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        LazySingletonSync instance1 = LazySingletonSync.getInstance();
        LazySingletonSync instance2 = LazySingletonSync.getInstance();

        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println();
    }

    /**
     * 演示双重检查锁
     */
    private static void demonstrateDoubleCheckLock() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              3. 双重检查锁单例");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        DoubleCheckLockSingleton instance1 = DoubleCheckLockSingleton.getInstance();
        DoubleCheckLockSingleton instance2 = DoubleCheckLockSingleton.getInstance();

        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println();
    }

    /**
     * 演示静态内部类
     */
    private static void demonstrateStaticInnerClass() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              4. 静态内部类单例");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        StaticInnerClassSingleton instance1 = StaticInnerClassSingleton.getInstance();
        StaticInnerClassSingleton instance2 = StaticInnerClassSingleton.getInstance();

        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println();
    }

    /**
     * 演示枚举单例
     */
    private static void demonstrateEnumSingleton() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              5. 枚举单例");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        EnumSingleton instance2 = EnumSingleton.INSTANCE;

        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println("instance1.getInfo(): " + instance1.getInfo());
        System.out.println();
    }

    /**
     * 演示连接池单例
     */
    private static void demonstrateConnectionPool() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              6. 数据库连接池管理器");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // 初始化连接池
        DatabaseConfig config = new DatabaseConfig(
            "jdbc:mysql://localhost:3306/mydb",
            "root",
            "password",
            5
        );
        ConnectionPoolManager pool = ConnectionPoolManager.getInstance(config);

        // 获取连接
        String conn1 = pool.getConnection();
        String conn2 = pool.getConnection();

        // 查看状态
        pool.printPoolStatus();

        // 释放连接
        pool.releaseConnection(conn1);
        pool.releaseConnection(conn2);

        // 再次获取连接
        String conn3 = pool.getConnection();
        pool.printPoolStatus();
    }

    /**
     * 演示注册式单例
     */
    private static void demonstrateSingletonRegistry() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              7. 注册式单例（容器管理）");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // 注册单例
        SingletonRegistry.register("userCache", new HashMap<String, String>());
        SingletonRegistry.register("configCache", new Properties());

        // 获取单例
        @SuppressWarnings("unchecked")
        Map<String, String> userCache = SingletonRegistry.getInstance("userCache", Map.class);

        // 使用单例
        userCache.put("user1", "张三");
        userCache.put("user2", "李四");

        System.out.println("已注册的单例: " + SingletonRegistry.getAllKeys());
        System.out.println("用户缓存内容: " + userCache);
    }
}
```

---

#### 反射与序列化破坏单例

单例模式可能被反射或序列化破坏，需要防御。

```java
import java.io.*;

// ==================== 防反射攻击 ====================

/**
 * 防反射攻击的单例
 */
class ReflectionSafeSingleton {
    private static ReflectionSafeSingleton instance;

    private ReflectionSafeSingleton() {
        // 防止反射创建实例
        if (instance != null) {
            throw new IllegalStateException("单例已被创建，禁止通过反射创建新实例");
        }
        System.out.println("🔄 防反射单例：实例已创建");
    }

    public static synchronized ReflectionSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ReflectionSafeSingleton();
        }
        return instance;
    }
}

// ==================== 防序列化破坏 ====================

/**
 * 防序列化破坏的单例
 */
class SerializableSingleton implements Serializable {
    private static final long serialVersionUID = 1L;

    private static SerializableSingleton instance;

    private SerializableSingleton() {
        System.out.println("🔄 防序列化单例：实例已创建");
    }

    public static synchronized SerializableSingleton getInstance() {
        if (instance == null) {
            instance = new SerializableSingleton();
        }
        return instance;
    }

    /**
     * 防止序列化破坏单例
     * 反序列化时，readResolve 方法会被调用，返回指定的单例实例
     */
    private Object readResolve() {
        return getInstance();
    }
}

// ==================== 演示代码 ====================

class SingletonSafetyDemo {
    public static void main(String[] args) throws Exception {
        // ── 演示反射破坏 ──
        demonstrateReflectionAttack();

        // ── 演示序列化破坏 ──
        demonstrateSerializationAttack();
    }

    /**
     * 演示反射攻击
     */
    private static void demonstrateReflectionAttack() throws Exception {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🛡️  反射攻击演示                              ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 获取正常实例
        ReflectionSafeSingleton instance1 = ReflectionSafeSingleton.getInstance();

        try {
            // 尝试通过反射创建新实例
            java.lang.reflect.Constructor<ReflectionSafeSingleton> constructor =
                ReflectionSafeSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ReflectionSafeSingleton instance2 = constructor.newInstance();

            System.out.println("反射创建成功！");
        } catch (IllegalStateException e) {
            System.out.println("❌ 反射攻击被阻止: " + e.getMessage());
        }
    }

    /**
     * 演示序列化攻击
     */
    private static void demonstrateSerializationAttack() throws Exception {
        System.out.println("\n╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🛡️  序列化攻击演示                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 获取实例
        SerializableSingleton instance1 = SerializableSingleton.getInstance();
        System.out.println("原始实例: " + instance1.hashCode());

        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(instance1);
        oos.close();

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        SerializableSingleton instance2 = (SerializableSingleton) ois.readObject();
        ois.close();

        System.out.println("反序列化实例: " + instance2.hashCode());
        System.out.println("instance1 == instance2: " + (instance1 == instance2));
    }
}
```

---

#### 实际应用：Spring Bean 单例

Spring 框架默认使用单例模式管理 Bean。

```
                    Spring Bean 单例管理

   ApplicationContext
            │
            │ getBean("userService")
            ↓
         BeanFactory
            │
            │ 1. 检查缓存
            │ 2. 创建 Bean（如不存在）
            │ 3. 缓存 Bean
            ↓
       ┌──────────────────┐
       │  singletonObjects │
       │    (缓存池)       │
       └────────┬─────────┘
                │
                ↓
           返回 Bean 实例
```

**Spring Bean 单例示例**

```java
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;

/**
 * 用户服务 - Spring 单例 Bean
 */
@Service
public class UserService {

    private int requestCount = 0;

    public void handleRequest() {
        requestCount++;
        System.out.println("处理请求 #" + requestCount);
        System.out.println("当前 Bean: " + this.hashCode());
    }

    public int getRequestCount() {
        return requestCount;
    }
}

/**
 * 配置类
 */
@Configuration
@ComponentScan
public class AppConfig {
    // 默认情况下，@Service 注解的 Bean 就是单例
    // 等价于 @Scope("singleton")
}

/**
 * Spring 单例演示
 */
public class SpringSingletonDemo {
    public static void main(String[] args) {
        // 创建 Spring 容器
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

        // 获取两次 Bean
        UserService userService1 = context.getBean(UserService.class);
        UserService userService2 = context.getBean(UserService.class);

        System.out.println("userService1 == userService2: " + (userService1 == userService2));
        System.out.println("userService1 hashCode: " + userService1.hashCode());
        System.out.println("userService2 hashCode: " + userService2.hashCode());

        // 调用方法验证是同一个实例
        userService1.handleRequest();
        userService2.handleRequest();
        System.out.println("总请求数: " + userService1.getRequestCount());

        context.close();
    }
}
```

**Spring Bean 作用域对比**

| 作用域 | 说明 | 实例数量 |
|:------|:----|:--------:|
| singleton | 单例（默认） | 每个容器 1 个 |
| prototype | 原型，每次请求创建新实例 | 每次请求 1 个 |
| request | 每个 HTTP 请求一个实例 | 每个 HTTP 请求 1 个 |
| session | 每个 HTTP Session 一个实例 | 每个 Session 1 个 |
| application | 整个 Web 应用一个实例 | 每个应用 1 个 |

---

#### 实际应用：Runtime 类

JDK 中的 Runtime 类是经典的单例模式应用。

```java
import java.io.IOException;

/**
 * JDK Runtime 类单例演示
 */
class RuntimeSingletonDemo {
    public static void main(String[] args) throws IOException {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            💻 JDK Runtime 单例演示                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 获取 Runtime 实例（单例）
        Runtime runtime1 = Runtime.getRuntime();
        Runtime runtime2 = Runtime.getRuntime();

        System.out.println("runtime1 == runtime2: " + (runtime1 == runtime2));
        System.out.println("Available Processors: " + runtime1.availableProcessors());
        System.out.println("Free Memory: " + (runtime1.freeMemory() / 1024 / 1024) + " MB");
        System.out.println("Max Memory: " + (runtime1.maxMemory() / 1024 / 1024) + " MB");
        System.out.println("Total Memory: " + (runtime1.totalMemory() / 1024 / 1024) + " MB");

        // 执行系统命令
        System.out.println("\n执行系统命令:");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Process process = runtime1.exec("cmd /c echo Hello from Java!");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("   " + line);
            }
        }
    }
}
```

---

#### JDK 中的单例类

| 类名 | 获取方式 | 说明 |
|:----|:--------|:----|
| `Runtime` | `Runtime.getRuntime()` | 运行时环境 |
| `Desktop` | `Desktop.getDesktop()` | 桌面操作 |
| `System` | `System.out/in/err` | 标准输入输出（类本身就是单例） |
| `Collections.EMPTY_*` | 常量引用 | 空集合单例 |
| `Optional.empty()` | 静态方法 | 空 Optional 单例 |

---

#### 优缺点对比

| 类型 | 说明 |
|:----|:----|
| ✅ 优点 | • 内存中只有一个实例，减少内存开销<br>• 避免对资源的多重占用<br>• 设置全局访问点，优化共享资源访问<br>• 实现简单 |
| ❌ 缺点 | • 违反单一职责原则（单例类既要负责业务，又要负责自身管理）<br>• 不容易扩展<br>• 单例类持有状态时可能导致问题<br>• 测试困难（单例状态持久化） |

---

#### 适用场景

| 场景 | 示例 |
|:----:|:----|
| 1 | 数据库连接池 |
| 2 | 配置管理器 |
| 3 | 日志记录器 |
| 4 | 缓存管理 |
| 5 | 线程池 |
| 6 | 系统工具类（Runtime） |

---

#### 总结

| 概念 | 说明 |
|:----|:----|
| 核心思想 | 确保一个类只有一个实例 |
| 推荐实现 | 静态内部类、枚举 |
| 注意事项 | 防反射、防序列化破坏 |
| 应用场景 | 资源管理、配置管理、缓存 |