### 什么是代理模式？

代理模式为其他对象提供一种代理以控制对这个对象的访问。代理对象在客户端和目标对象之间起到中介作用，可以添加额外的功能（如权限控制、延迟加载、日志记录等）。

#### 核心思想

通过代理对象间接访问目标对象，在不修改目标对象的情况下增强或控制其功能。

---

#### 工作原理

```
                    客户端
                       │
                       │ 请求
                       ↓
              ┌────────────────┐
              │    Proxy       │
              │   (代理对象)    │
              │  • 预处理请求   │
              │  • 控制访问权限 │
              │  • 延迟加载     │
              └────────┬───────┘
                       │ 委托调用
                       ↓
              ┌────────────────┐
              │  RealSubject   │
              │  (真实对象)     │
              │  • 业务逻辑     │
              │  • 数据访问     │
              └────────────────┘
```

---

#### 核心角色

| 角色 | 说明 | 职责 |
|:------------|:------------|:----------------------------------|
| **Subject** | 抽象主题 | 声明真实主题和代理的共同接口 |
| **RealSubject** | 真实主题 | 定义代理所代表的真实对象 |
| **Proxy** | 代理 | 持有真实主题的引用，控制对其访问 |

---

#### 代理类型

| 类型 | 说明 | 应用场景 |
|:----|:----|:----|
| **静态代理** | 代理类在编译时确定 | 代码简单，功能固定 |
| **动态代理** | 代理类在运行时动态生成 | 需要灵活控制多个类 |
| **远程代理** | 代表不同地址空间中的对象 | 分布式系统、RPC |
| **虚拟代理** | 延迟创建资源消耗大的对象 | 图片加载、大数据查询 |
| **保护代理** | 控制对原对象的访问权限 | 权限验证、敏感操作 |

---

#### 完整代码示例

**场景：图片加载系统 - 虚拟代理模式**

```java
import java.util.HashMap;
import java.util.Map;

// ==================== 抽象主题 ====================

/**
 * 图片接口 - 抽象主题
 */
interface Image {
    /**
     * 显示图片
     */
    void display();

    /**
     * 获取图片信息
     *
     * @return 图片信息字符串
     */
    String getImageInfo();
}

// ==================== 真实主题 ====================

/**
 * 高清图片 - 真实主题
 * 实际加载和显示图片的类
 */
class HighResolutionImage implements Image {

    // ── 基本属性 ──
    private String filename;        // 文件名
    private String title;           // 图片标题
    private int width;             // 宽度
    private int height;            // 高度
    private byte[] imageData;      // 图片数据
    private boolean isLoaded;      // 是否已加载

    // ── 构造方法 ──

    /**
     * 构造方法
     *
     * @param filename 文件名
     * @param title 图片标题
     * @param width 宽度
     * @param height 高度
     */
    public HighResolutionImage(String filename, String title, int width, int height) {
        this.filename = filename;
        this.title = title;
        this.width = width;
        this.height = height;
        this.isLoaded = false;
    }

    @Override
    public void display() {
        // 懒加载：第一次显示时才真正加载
        if (!isLoaded) {
            loadImage();
        }
        renderImage();
    }

    @Override
    public String getImageInfo() {
        return String.format("高清图片 [%s] - %dx%d - %s",
                filename, width, height, title);
    }

    /**
     * 加载图片数据
     * 模拟从磁盘或网络加载大文件的耗时操作
     */
    private void loadImage() {
        System.out.println("📦 正在从磁盘加载高清图片...");
        System.out.println("   文件: " + filename);

        // 模拟加载过程
        simulateLoadingProgress();

        // 生成模拟数据
        this.imageData = new byte[width * height * 4]; // RGBA格式
        this.isLoaded = true;

        System.out.println("✅ 图片加载完成！大小: " + (imageData.length / 1024) + " KB");
    }

    /**
     * 渲染图片
     */
    private void renderImage() {
        System.out.println("🖼️  正在渲染高清图片...");
        System.out.println("   标题: " + title);
        System.out.println("   分辨率: " + width + "x" + height);
        System.out.println("   数据大小: " + imageData.length + " 字节");
    }

    /**
     * 模拟加载进度
     */
    private void simulateLoadingProgress() {
        try {
            for (int i = 20; i <= 100; i += 20) {
                Thread.sleep(200);
                System.out.println("   加载进度: " + i + "%");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ── Getter 方法 ──

    public String getFilename() { return filename; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isLoaded() { return isLoaded; }
}

// ==================== 代理对象 ====================

/**
 * 图片代理 - 虚拟代理
 * 延迟加载图片，只在真正需要显示时才加载
 */
class ImageProxy implements Image {

    // ── 基本属性 ──
    private String filename;                // 文件名
    private String title;                   // 图片标题
    private int width;                     // 宽度
    private int height;                    // 高度

    // ── 代理控制 ──
    private HighResolutionImage realImage;  // 真实图片对象（延迟创建）
    private boolean isLoaded;              // 是否已请求加载

    // ── 缩略图缓存 ──
    private String thumbnail;               // 缩略图数据

    // ── 构造方法 ──

    /**
     * 构造方法
     *
     * @param filename 文件名
     * @param title 图片标题
     * @param width 宽度
     * @param height 高度
     */
    public ImageProxy(String filename, String title, int width, int height) {
        this.filename = filename;
        this.title = title;
        this.width = width;
        this.height = height;
        this.isLoaded = false;

        // 创建缩略图（轻量级操作）
        generateThumbnail();
    }

    @Override
    public void display() {
        if (!isLoaded) {
            // 首次显示：先展示缩略图
            displayThumbnail();

            // 询问用户是否加载高清图
            System.out.println("\n💡 提示：按 Enter 加载高清图片，或跳过...");
            // 实际应用中可能是用户点击"查看高清"按钮

            // 延迟创建真实图片对象
            System.out.println("\n⏳ 创建真实图片对象...");
            realImage = new HighResolutionImage(filename, title, width, height);
            isLoaded = true;
        }

        // 委托给真实对象
        realImage.display();
    }

    @Override
    public String getImageInfo() {
        return String.format("图片代理 [%s] - %dx%d - %s (未加载)",
                filename, width, height, title);
    }

    /**
     * 显示缩略图
     */
    private void displayThumbnail() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          🖼️  显示缩略图");
        System.out.println("=".repeat(50));
        System.out.println("   标题: " + title);
        System.out.println("   文件: " + filename);
        System.out.println("   缩略图数据: " + thumbnail);
        System.out.println("   原始尺寸: " + width + "x" + height);
        System.out.println("=".repeat(50));
    }

    /**
     * 生成缩略图
     * 这是一个轻量级操作，快速完成
     */
    private void generateThumbnail() {
        // 模拟生成缩略图
        this.thumbnail = "[缩略图数据: 64x64]";
        System.out.println("🖼️  图片代理创建: " + title + " (" + filename + ")");
        System.out.println("   ✅ 缩略图已生成（轻量级）");
        System.out.println("   ⏳ 高清图将在需要时加载");
    }

    // ── Getter 方法 ──

    public String getFilename() { return filename; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isRealImageLoaded() { return isLoaded; }
}

// ==================== 保护代理示例 ====================

/**
 * 用户权限枚举
 */
enum Permission {
    READ, WRITE, ADMIN
}

/**
 * 用户类
 */
class User {
    private String username;
    private Permission permission;

    public User(String username, Permission permission) {
        this.username = username;
        this.permission = permission;
    }

    public String getUsername() { return username; }
    public Permission getPermission() { return permission; }
}

/**
 * 敏感文件接口 - 抽象主题
 */
interface SensitiveFile {
    void read(User user);
    void write(User user, String content);
    void delete(User user);
}

/**
 * 真实文件 - 真实主题
 */
class RealFile implements SensitiveFile {
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

/**
 * 文件代理 - 保护代理
 * 控制对敏感文件的访问权限
 */
class FileProxy implements SensitiveFile {
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

// ==================== 客户端代码 ====================

/**
 * 代理模式演示
 */
class ProxyPatternDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║            🖼️  代理模式 - 图片加载系统                   ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // ── 虚拟代理演示 ──
        demonstrateVirtualProxy();

        // ── 保护代理演示 ──
        demonstrateProtectionProxy();
    }

    /**
     * 演示虚拟代理（延迟加载）
     */
    private static void demonstrateVirtualProxy() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              1. 虚拟代理 - 延迟加载");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // 创建图片列表
        System.out.println("📚 创建图片列表...\n");
        Image[] images = {
            new ImageProxy("sunset.jpg", "美丽日落", 3840, 2160),
            new ImageProxy("mountain.jpg", "雪山美景", 4000, 3000),
            new ImageProxy("city.jpg", "城市夜景", 5000, 3000)
        };

        // 注意：此时真实图片并未加载，只生成了缩略图
        System.out.println("\n✅ 图片列表创建完成（仅加载了缩略图）\n");

        // 模拟用户浏览图片
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              用户浏览图片");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // 显示第一张图片（触发加载）
        images[0].display();

        // 显示第二张图片（触发加载）
        System.out.println("\n─────────────────────────────────────────────────────────");
        images[1].display();
    }

    /**
     * 演示保护代理（权限控制）
     */
    private static void demonstrateProtectionProxy() {
        System.out.println("\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("              2. 保护代理 - 权限控制");
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
```

---

#### 实际应用：JDK 动态代理

JDK 动态代理可以在运行时动态创建代理类，无需手动编写代理类代码。

```
                    JDK 动态代理架构

   InvocationHandler (调用处理器)
            │
            │ invoke(proxy, method, args)
            ↓
         Proxy.newProxyInstance()
            │
            │ 创建动态代理实例
            ↓
       ┌──────────────┐
       │ $Proxy0      │  ← 运行时生成的代理类
       │   (动态代理)  │
       └──────┬───────┘
              │ 调用方法
              ↓
       ┌──────────────┐
       │  目标对象    │
       │  (Target)    │
       └──────────────┘
```

**JDK 动态代理示例**

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// ==================== 业务接口 ====================

/**
 * 用户服务接口
 */
interface UserService {
    void addUser(String username, String email);
    void deleteUser(String username);
    String getUser(String username);
}

// ==================== 目标对象 ====================

/**
 * 用户服务实现 - 目标对象
 */
class UserServiceImpl implements UserService {
    private Map<String, String> users = new HashMap<>();

    @Override
    public void addUser(String username, String email) {
        users.put(username, email);
        System.out.println("✅ 用户已添加: " + username + " <" + email + ">");
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
        System.out.println("🗑️  用户已删除: " + username);
    }

    @Override
    public String getUser(String username) {
        String email = users.get(username);
        System.out.println("🔍 查询用户: " + username + " -> " + email);
        return email;
    }
}

// ==================== 调用处理器 ====================

/**
 * 日志调用处理器
 * 在方法调用前后添加日志功能
 */
class LoggingInvocationHandler implements InvocationHandler {

    private Object target;  // 目标对象

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置处理
        long startTime = System.currentTimeMillis();
        System.out.println("\n" + "─".repeat(50));
        System.out.println("🔔 [调用前] " + method.getName());
        System.out.println("   参数: " + java.util.Arrays.toString(args));
        System.out.println("─".repeat(50));

        try {
            // 调用目标对象的方法
            Object result = method.invoke(target, args);

            // 后置处理
            long endTime = System.currentTimeMillis();
            System.out.println("─".repeat(50));
            System.out.println("✅ [调用后] " + method.getName() + " 完成");
            System.out.println("   返回值: " + result);
            System.out.println("   执行时间: " + (endTime - startTime) + " ms");
            System.out.println("─".repeat(50) + "\n");

            return result;
        } catch (Exception e) {
            // 异常处理
            System.out.println("❌ [异常] " + method.getName() + " 执行失败");
            System.out.println("   异常信息: " + e.getMessage());
            throw e;
        }
    }
}

/**
 * 权限控制调用处理器
 */
class PermissionInvocationHandler implements InvocationHandler {

    private Object target;
    private String currentUser;

    public PermissionInvocationHandler(Object target, String currentUser) {
        this.target = target;
        this.currentUser = currentUser;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 检查权限
        String methodName = method.getName();
        if (methodName.equals("deleteUser") && !currentUser.equals("admin")) {
            throw new SecurityException("权限不足: 只有管理员可以删除用户");
        }

        if (methodName.equals("addUser") && !currentUser.equals("admin") &&
            !currentUser.equals("editor")) {
            throw new SecurityException("权限不足: 只有管理员和编辑可以添加用户");
        }

        // 权限通过，执行方法
        System.out.println("✅ 权限验证通过: " + currentUser);
        return method.invoke(target, args);
    }
}

/**
 * 动态代理工厂
 */
class ProxyFactory {

    /**
     * 创建日志代理
     */
    public static <T> T createLogProxy(T target, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class<?>[] { interfaceClass },
            new LoggingInvocationHandler(target)
        );
    }

    /**
     * 创建权限代理
     */
    public static <T> T createPermissionProxy(T target, Class<T> interfaceClass, String user) {
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            new Class<?>[] { interfaceClass },
            new PermissionInvocationHandler(target, user)
        );
    }
}

// ==================== 客户端代码 ====================

/**
 * JDK 动态代理演示
 */
class DynamicProxyDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║          🔧 JDK 动态代理 - 日志与权限控制               ║");
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
```

---

#### 实际应用：CGLIB 动态代理

CGLIB 可以代理没有实现接口的类，通过子类继承的方式实现代理。

**CGLIB 动态代理示例**

```java
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// ==================== 目标类（无接口） ====================

/**
 * 订单服务 - 没有实现任何接口
 */
class OrderService {
    public void createOrder(String orderId, double amount) {
        System.out.println("✅ 订单已创建: " + orderId + ", 金额: ¥" + amount);
    }

    public void cancelOrder(String orderId) {
        System.out.println("🗑️  订单已取消: " + orderId);
    }

    public String getOrderStatus(String orderId) {
        return "PAID";
    }
}

// ==================== 方法拦截器 ====================

/**
 * 事务拦截器
 */
class TransactionInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String methodName = method.getName();

        // 开启事务
        System.out.println("\n" + "─".repeat(50));
        System.out.println("🔐 [事务开始] " + methodName);
        System.out.println("─".repeat(50));

        try {
            // 执行目标方法
            Object result = proxy.invokeSuper(obj, args);

            // 提交事务
            System.out.println("─".repeat(50));
            System.out.println("✅ [事务提交] " + methodName);
            System.out.println("─".repeat(50) + "\n");

            return result;
        } catch (Exception e) {
            // 回滚事务
            System.out.println("─".repeat(50));
            System.out.println("❌ [事务回滚] " + methodName);
            System.out.println("   错误: " + e.getMessage());
            System.out.println("─".repeat(50) + "\n");
            throw e;
        }
    }
}

// ==================== 代理工厂 ====================

/**
 * CGLIB 代理工厂
 */
class CglibProxyFactory {

    /**
     * 创建 CGLIB 代理
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> targetClass, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }
}

// ==================== 客户端代码 ====================

/**
 * CGLIB 动态代理演示
 */
class CglibProxyDemo {
    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════════════════╗");
        System.out.println("║         🎯 CGLIB 动态代理 - 事务管理示例                 ║");
        System.out.println("╚═════════════════════════════════════════════════════════╝\n");

        // 创建代理
        OrderService orderService = CglibProxyFactory.createProxy(
            OrderService.class, new TransactionInterceptor()
        );

        // 执行业务操作
        orderService.createOrder("ORD001", 99.99);
        orderService.getOrderStatus("ORD001");
        orderService.cancelOrder("ORD001");
    }
}
```

---

#### 实际应用：Spring AOP

Spring AOP 基于代理模式实现面向切面编程。

```
                    Spring AOP 代理架构

        Target (目标对象)
              │
              │ 被 Proxy 包装
              ↓
       ┌──────────────┐
       │  AOP Proxy   │
       │  (代理对象)   │
       └──────┬───────┘
              │
    ┌─────────┼─────────┬─────────────┬────────────┐
    │         │         │             │            │
    ↓         ↓         ↓             ↓            ↓
@Before @After @AfterReturning @AfterThrowing @Around
   │         │         │             │            │
   └─────────┴─────────┴─────────────┴────────────┘
                         │
                         ↓
                   目标方法执行
```

**Spring AOP 示例**

```java
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// ==================== 切面 ====================

/**
 * 日志切面
 */
@Aspect
@Component
class LoggingAspect {

    /**
     * 定义切入点：匹配所有 service 包下的方法
     */
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() {}

    /**
     * 前置通知：方法执行前
     */
    @Before("serviceMethods()")
    public void beforeMethod() {
        System.out.println("🔔 [前置通知] 方法即将执行");
    }

    /**
     * 后置通知：方法执行后
     */
    @After("serviceMethods()")
    public void afterMethod() {
        System.out.println("✅ [后置通知] 方法执行完成");
    }

    /**
     * 返回通知：方法返回结果后
     */
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturning(Object result) {
        System.out.println("📤 [返回通知] 方法返回: " + result);
    }

    /**
     * 异常通知：方法抛出异常时
     */
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        System.out.println("❌ [异常通知] 方法抛出异常: " + ex.getMessage());
    }

    /**
     * 环绕通知：完全控制方法执行
     */
    @Around("serviceMethods()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        System.out.println("⏱️  [环绕通知] 执行时间: " + (endTime - startTime) + " ms");

        return result;
    }
}

/**
 * 事务切面
 */
@Aspect
@Component
class TransactionAspect {

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beginTransaction() {
        System.out.println("🔐 开始事务");
    }

    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void commitTransaction() {
        System.out.println("✅ 提交事务");
    }

    @AfterThrowing("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void rollbackTransaction() {
        System.out.println("❌ 回滚事务");
    }
}

// ==================== 目标对象 ====================

@Service
class ProductService {

    @Transactional
    public void createProduct(String productId, String name, double price) {
        System.out.println("创建产品: " + name);
    }

    @Transactional
    public void updatePrice(String productId, double newPrice) {
        System.out.println("更新价格: " + newPrice);
    }
}
```

---

#### 优缺点对比

| 类型 | 说明 |
|:----|:----|
| ✅ 优点 | • 职责清晰：代理和真实对象各司其职<br>• 延迟加载：提升性能<br>• 权限控制：增强安全性<br>• AOP 支持：面向切面编程<br>• 无需修改原有代码 |
| ❌ 缺点 | • 增加系统复杂度<br>• 可能影响响应速度（多层代理）<br>• 静态代理代码冗余（一个接口对应一个代理类） |

---

#### 适用场景

| 场景 | 示例 |
|:----:|:----|
| 1 | 远程代理（RPC调用） |
| 2 | 虚拟代理（图片延迟加载） |
| 3 | 保护代理（权限控制） |
| 4 | 智能引用（缓存、计数） |
| 5 | AOP 面向切面编程 |

---

#### 总结

| 概念 | 说明 |
|:----|:----|
| 核心思想 | 通过代理间接访问目标对象 |
| 代理类型 | 静态代理、动态代理（JDK/CGLIB） |
| 应用场景 | 延迟加载、权限控制、AOP、RPC |
