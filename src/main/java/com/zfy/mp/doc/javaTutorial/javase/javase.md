# Java 面试题 - Java 基础部分

> 本文档记录 Java 基础相关的面试问题和回答

---

## Q1: 谈谈你对AQS的理解？

**AQS（AbstractQueuedSynchronizer）** 是 `java.util.concurrent.locks` 包下的一个抽象类，是 Java 并发包的基础框架。

### 核心思想

AQS 基于 **state 变量** 和 **CLH 队列** 来实现同步：

| 组成 | 说明 |
|------|------|
| state | 一个 volatile int 变量，表示同步状态（0=未获取，>0=已获取） |
| CLH 队列 | 双向链表，存储等待获取锁的线程 |

### 工作流程

1. **获取锁**：线程尝试 CAS 修改 state，成功则获得锁
2. **获取失败**：线程封装成 Node 节点，加入队列尾部等待
3. **释放锁**：修改 state，唤醒队列头节点的后继节点

### Node 节点状态

```
CANCELLED(1)   - 线程已取消
SIGNAL(-1)     - 后继节点需要唤醒
CONDITION(-2)  - 线程在 Condition 上等待
PROPAGATE(-3)  - 共享模式下传播唤醒
```

### 应用场景

AQS 是许多并发类的底层实现：

- **独占锁**：`ReentrantLock`、`ReentrantReadWriteLock.WriteLock`
- **共享锁**：`CountDownLatch`、`Semaphore`、`ReentrantReadWriteLock.ReadLock`

### 独占 vs 共享模式

| 模式 | 特点 | 示例 |
|------|------|------|
| 独占 | 同一时间只能一个线程持有 | ReentrantLock |
| 共享 | 同一时间可多个线程持有 | Semaphore |

### 简单示例

ReentrantLock 的 AQS 实现：
- 获取锁：`state` 从 0 改为 1（可重入则 state++）
- 释放锁：`state` 减 1，为 0 时唤醒等待线程

### 总结

AQS 是 Java 并发包的基石，通过 state + 队列的巧妙设计，为各种同步器提供了统一的实现框架。理解 AQS 有助于掌握 ReentrantLock、CountDownLatch 等工具的底层原理。

---

## Q2: fail-safe机制与fail-fast机制分别有什么作用？

**Fail-fast（快速失败）** 和 **Fail-safe（安全失败）** 是集合遍历时的两种不同策略。

### 对比总结

| 特性 | Fail-fast | Fail-safe |
|------|-----------|-----------|
| 异常抛出 | 遍历时修改集合抛 `ConcurrentModificationException` | 不抛异常 |
| 底层实现 | 直接操作原集合 | 操作原集合的副本 |
| 性能 | 性能较好 | 需要复制，性能较低 |
| 数据一致性 | 遍历的是最新数据 | 遍历的是快照数据 |
| 代表集合 | ArrayList、HashMap、HashSet | CopyOnWriteArrayList、ConcurrentHashMap |

### Fail-fast 机制

**原理**：集合内部维护一个 `modCount` 计数器，每次修改集合（增、删、改）都会自增。

```
Iterator 遍历时：
1. 记录遍历开始时的 expectedModCount
2. 每次迭代检查 expectedModCount == modCount
3. 不相等则抛出 ConcurrentModificationException
```

**示例**：
```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    if ("B".equals(s)) {
        list.remove("B");  // 直接修改原集合 -> 抛出异常！
    }
}
// ConcurrentModificationException
```

**正确做法**：使用迭代器的 `remove()` 方法
```java
while (it.hasNext()) {
    String s = it.next();
    if ("B".equals(s)) {
        it.remove();  // 使用 iterator.remove() -> 正常
    }
}
```

### Fail-safe 机制

**原理**：遍历时先复制原集合，在副本上进行操作，原集合的修改不影响遍历。

**示例**：
```java
List<String> list = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));

for (String s : list) {
    if ("B".equals(s)) {
        list.add("D");  // 不抛异常！
    }
}
// 遍历结果：A, B, C（新添加的 D 遍历不到）
```

### 应用场景

- **Fail-fast**：普通场景，追求性能，用完即弃的临时集合
- **Fail-safe**：并发场景，遍历时可能被其他线程修改

### 总结

- **Fail-fast**：发现修改立即报错，"快速失败"，避免不一致
- **Fail-safe**：遍历不受修改影响，"安全失败"，但可能读到旧数据

---

## Q3: CPU飙高系统反应慢怎么排查？

这是一个经典的生产问题排查面试题，需要有一套清晰的排查思路。

### 排查流程图

```
发现问题 → 定位进程 → 定位线程 → 分析堆栈 → 修复问题
```

### 详细步骤

#### 第一步：确认问题现象

| 现象 | 可能原因 |
|------|----------|
| CPU 100% | 死循环、复杂计算 |
| CPU 持续高位 | 频繁 GC、算法效率低 |
| CPU 周期性飙升 | 定时任务、周期性操作 |

#### 第二步：定位占用 CPU 高的 Java 进程

```bash
# 查看 CPU 使用率，按 CPU 排序
top -c

# 或者使用 ps 查找 Java 进程
ps -ef | grep java

# 记录 PID，例如：12345
```

#### 第三步：定位占用 CPU 高的线程

```bash
# 方法1：使用 top 查看线程
top -H -p 12345
# 按 P 按 CPU 排序，找到高 CPU 线程的 tid

# 方法2：使用 ps 查看线程
ps -mp 12345 -o THREAD,tid,time

# 记录 tid（十进制），例如：13456
```

#### 第四步：将线程 ID 转为十六进制

```bash
# 将十进制 tid 转为十六进制（用于和 jstack 输出对应）
printf "%x" 13456
# 输出：3490
```

#### 第五步：查看线程堆栈

```bash
# 打印线程堆栈
jstack 12345 | grep -A 20 3490
# 或者
jstack 12345 > thread_dump.txt
```

#### 第六步：分析堆栈，定位问题代码

根据堆栈信息，定位到具体的代码行号。

### 常见问题及解决方案

| 问题类型 | 典型代码特征 | 解决方案 |
|----------|--------------|----------|
| **死循环** | while(true) 无退出条件 | 添加退出条件、优化逻辑 |
| **正则回溯** | 复杂正则匹配 | 优化正则表达式 |
| **频繁 GC** | 大对象频繁创建/回收 | 复用对象、调优 GC |
| **锁竞争** | synchronized/ReentrantLock | 减小锁粒度、优化锁设计 |
| **死锁** | 线程互相等待 | 持锁顺序一致、避免嵌套锁 |

### 实际排查示例

```bash
# 1. 找到 Java 进程 PID
$ top -c
# PID    %CPU
# 12345  350.0  ← 这个进程占用很高

# 2. 找到高 CPU 线程
$ top -H -p 12345
# PID    %CPU
# 13456  89.0   ← 这个线程占用很高

# 3. 转换线程 ID
$ printf "%x" 13456
# 3490

# 4. 查看堆栈
$ jstack 12345 | grep -A 30 3490
"Thread-2" #23 prio=5 os_prio=0 tid=0x00007f8c3401b800 nid=0x3490 runnable [0x00007f8c321f3000]
   java.lang.Thread.State: RUNNABLE
        at com.example.MyService.processData(MyService.java:156)
        - locked <0x00000006f...> (a java.lang.Object)
```

### 其他常用排查命令

```bash
# GC 情况
jstat -gcutil 12345 1000

# 内存堆情况
jmap -heap 12345

# 导出堆内存快照
jmap -dump:live,format=b,file=heap.hprof 12345

# 实时监控 Java 进程
jconsole / jvisualvm / Arthas
```

### Arthas 快速排查（阿里开源）

```bash
# 启动 Arthas
./arthas-boot.sh

# 查看线程 CPU 排名
thread -n 5

# 查看指定线程堆栈
thread <thread-id>

# 方法调用次数统计
profiler start
profiler stop --output火焰图.html
```

### 总结

**排查口诀**：
```
一找进程（top），二找线程（top -H）
三转进制（printf %x），四看堆栈（jstack）
五看代码（定位），六修bug（解决）
```

---

## Q4: 什么是受检异常和非受检异常？

Java 异常分为两大类：**受检异常** 和 **非受检异常**。

### 异常体系图

```
                    Throwable
                     /      \
                  Error    Exception
                         /         \
                受检异常         非受检异常 (RuntimeException)
```

### 对比总结

| 特性 | 受检异常 | 非受检异常 |
|------|---------------------|-------------------|
| 继承类 | Exception | RuntimeException |
| 编译期检查 | ✅ 必须处理 | ❌ 不强制 |
| 处理方式 | throws 或 try-catch | 可处理可不处理 |
| 代表异常 | IOException, SQLException | NullPointerException, ArithmeticException |

### 受检异常（Checked Exception）

**定义**：编译期强制要求处理的异常，如果不处理代码无法通过编译。

**常见受检异常**：
```java
IOException        // IO 操作异常
SQLException       // SQL 操作异常
ClassNotFoundException   // 类未找到异常
FileNotFoundException    // 文件未找到异常
ParseException      // 解析异常
```

**处理方式**：
```java
// 方式1：使用 try-catch
public void readFile() {
    try {
        FileReader reader = new FileReader("test.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}

// 方式2：使用 throws 声明
public void readFile() throws FileNotFoundException {
    FileReader reader = new FileReader("test.txt");
}
```

### 非受检异常（Unchecked Exception）

**定义**：编译期不强制要求处理的异常，通常是程序逻辑错误导致的。

**常见非受检异常**：
```java
NullPointerException          // 空指针异常
ArrayIndexOutOfBoundsException  // 数组越界
ClassCastException           // 类型转换异常
ArithmeticException          // 算术异常（如除以0）
IllegalArgumentException    // 非法参数异常
NumberFormatException       // 数字格式异常
```

**示例**：
```java
// 无需强制处理
public void divide() {
    int a = 10 / 0;  // 抛出 ArithmeticException，但编译不会报错
}

String str = null;
int len = str.length();  // 抛出 NullPointerException
```

### Error（错误）

**Error** 也是非受检的，但通常表示严重问题，不应捕获。

```
OutOfMemoryError    // 内存溢出
StackOverflowError  // 栈溢出
VirtualMachineError // 虚拟机错误
```

### 什么时候使用受检异常 vs 非受检异常？

| 场景 | 推荐类型 | 原因 |
|------|----------|------|
| 可恢复的业务异常 | 受检异常 | 强制调用者处理 |
| 不可预料的运行时错误 | 非受检异常 | 通常是代码bug，应修复而非处理 |
| 外部资源操作 | 受检异常 | IO、数据库等可能失败 |
| 参数校验失败 | 非受检异常 | 是调用方传错参数 |

### 示例对比

```java
// 受检异常：文件读取可能失败，调用者必须处理
public void processFile(String path) throws IOException {
    Files.readAllLines(Paths.get(path));
}

// 非受检异常：参数校验失败是调用方bug
public void setName(String name) {
    if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("name不能为空");
    }
}
```

### 总结

- **受检异常**：必须处理，用于可恢复的业务场景（如 IO、数据库）
- **非受检异常**：可不处理，用于程序错误场景（如空指针、参数错误）

**面试回答要点**：
1. 受检异常继承 Exception，编译期检查
2. 非受检异常继承 RuntimeException，编译期不检查
3. 受检异常用于可恢复的外部操作，非受检异常用于程序逻辑错误

---

## Q5: JDK动态代理为什么只能代理有接口的类？

这是 Java 动态代理机制的设计问题，需要从**实现原理**和**Java 单继承限制**两方面理解。

### 核心原因

```
Java 类单继承 + JDK 动态代理通过继承生成代理类 = 无法继承已有类
```

### JDK 动态代理的实现原理

JDK 动态代理在运行时**动态生成**一个代理类，这个代理类需要：

1. **继承** `Proxy` 类
2. **实现** 被代理的接口

```java
// JDK 生成的代理类结构（简化）
public final class $Proxy0 extends Proxy implements UserService {
    // 接口方法
    public void addUser() {
        h.invoke(this, method, args);  // 调用 InvocationHandler
    }
}
```

### 为什么只能代理接口？

因为 Java 类**只能继承一个父类**：

```
代理类必须继承 Proxy
   ↓
如果还要继承目标类（如 UserServiceImpl）
   ↓
冲突！Java 不支持多继承
```

### 对比：CGLIB 可以代理类

CGLIB 使用**字节码增强**，通过**继承目标类**生成子类：

```
// CGLIB 生成代理类结构
public final class UserServiceImpl$$EnhancerByCGLIB$$12345 extends UserServiceImpl {
    public void addUser() {
        // 前置逻辑
        super.addUser();  // 调用父类方法
        // 后置逻辑
    }
}
```

### JDK 动态代理示例

```java
// 接口
public interface UserService {
    void addUser(String name);
}

// 实现类
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(String name) {
        System.out.println("添加用户: " + name);
    }
}

// 创建代理
UserService proxy = (UserService) Proxy.newProxyInstance(
    UserServiceImpl.class.getClassLoader(),
    new Class[]{UserService.class},  // 接口数组
    (proxyObj, method, args) -> {
        System.out.println("前置逻辑");
        Object result = method.invoke(new UserServiceImpl(), args);
        System.out.println("后置逻辑");
        return result;
    }
);
```

### JDK vs CGLIB 对比

| 特性 | JDK 动态代理 | CGLIB |
|------|-------------|-------|
| 代理目标 | 只能代理接口 | 可代理类和接口 |
| 实现方式 | 继承 Proxy 类 | 继承目标类 |
| 性能 | JDK8 后更好 | 稍低 |
| final 方法限制 | 无 | 无法代理 final 方法 |
| 依赖 | JDK 原生 | 需要第三方库 |
| 框架默认 | Spring AOP | Spring AOP |

### Spring AOP 的选择策略

```java
// Spring AOP 自动选择代理方式
if (目标类实现了接口) {
    使用 JDK 动态代理
} else {
    使用 CGLIB
}

// 强制使用 CGLIB
@EnableAspectJAutoProxy(proxyTargetClass = true)
```

### 为什么 JDK 要这样设计？

1. **解耦**：面向接口编程，不依赖具体实现
2. **统一**：所有代理类都继承 Proxy，便于管理
3. **安全**：避免继承带来的复杂问题（如 super 调用链）

### 总结

| 问题 | 答案 |
|------|------|
| 为什么只能代理接口 | 代理类必须继承 Proxy，Java 不支持多继承 |
| CGLIB 如何解决 | 通过继承目标类生成子类 |
| 哪种更好 | 有接口用 JDK，无接口用 CGLIB |

**面试回答要点**：
1. JDK 动态代理生成代理类时，该类必须继承 `Proxy`
2. Java 类单继承，若要代理具体类需要同时继承 `Proxy` 和目标类，无法实现
3. CGLIB 通过继承目标类生成子类，可以代理类

---
