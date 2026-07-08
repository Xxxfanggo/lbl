# Java 知识体系

## 1. Java 基础

### 1.1 Java 语言特点

- 跨平台：一次编译，到处运行，依赖 JVM 实现平台无关性
- 面向对象：封装、继承、多态
- 自动内存管理：对象由 JVM 堆内存管理，通过 GC 回收
- 强类型语言：变量类型明确，编译期检查较严格
- 生态成熟：Spring、MyBatis、Netty、Dubbo、RocketMQ 等生态丰富

### 1.2 Java 程序运行流程

```text
Java 源码 .java
  -> javac 编译
  -> 字节码 .class
  -> 类加载器加载
  -> JVM 解释执行 / JIT 编译执行
  -> 操作系统执行机器指令
```

重点理解：

- `.java` 文件不是直接运行的，先编译成 `.class` 字节码
- JVM 屏蔽了不同操作系统的差异
- 热点代码会被 JIT 编译成本地机器码，提高执行效率

### 1.3 基本数据类型

Java 有 8 种基本数据类型：

```text
整数：byte、short、int、long
浮点：float、double
字符：char
布尔：boolean
```

常见面试点：

- `int` 默认整数类型，`double` 默认浮点类型
- `long` 定义时建议加 `L`
- `float` 定义时必须加 `F`
- 基本类型存值，引用类型存对象引用

### 1.4 包装类型

基本类型对应包装类型：

```text
int     -> Integer
long    -> Long
double  -> Double
boolean -> Boolean
```

重点：

- 包装类型可以为 `null`，基本类型不能
- 泛型不能使用基本类型，只能使用包装类型
- 自动装箱：`int -> Integer`
- 自动拆箱：`Integer -> int`

常见坑：

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true

Integer c = 128;
Integer d = 128;
System.out.println(c == d); // false
```

原因：`Integer` 默认缓存 `-128` 到 `127`。

## 2. 面向对象

### 2.1 封装

封装是把对象的属性和行为包装起来，对外只暴露必要接口。

常见体现：

- 成员变量使用 `private`
- 通过 `getter/setter` 访问
- 对外隐藏内部实现细节

### 2.2 继承

继承用于代码复用和表达父子关系。

特点：

- Java 只支持单继承
- 子类可以继承父类非私有属性和方法
- 子类可以重写父类方法

注意：

- 不要为了复用而滥用继承
- 继承表达的是 `is-a` 关系
- 组合很多时候比继承更灵活

### 2.3 多态

多态是同一个引用类型，在运行时表现出不同子类行为。

```java
Animal animal = new Dog();
animal.say();
```

条件：

- 有继承或接口实现
- 子类重写父类方法
- 父类引用指向子类对象

核心：

- 编译看左边
- 运行看右边

## 3. 常用关键字

### 3.1 static

`static` 表示属于类，而不是属于某个对象。

可以修饰：

- 静态变量
- 静态方法
- 静态代码块
- 静态内部类

特点：

- 类加载时初始化
- 可以通过类名直接访问
- 静态方法不能直接访问非静态成员

### 3.2 final

`final` 表示不可改变。

可以修饰：

- 类：不能被继承
- 方法：不能被重写
- 变量：只能赋值一次

常见问题：

- `final` 修饰引用类型时，引用地址不能变，但对象内容可以变

### 3.3 this 和 super

`this`：当前对象引用。

常见用途：

- 访问当前对象属性
- 调用当前类构造方法
- 解决局部变量和成员变量重名

`super`：父类对象引用。

常见用途：

- 调用父类构造方法
- 访问父类方法或属性

## 4. Object 类

所有 Java 类默认继承 `Object`。

常用方法：

- `equals`
- `hashCode`
- `toString`
- `clone`
- `getClass`
- `wait`
- `notify`
- `notifyAll`

### 4.1 equals 和 ==

`==`：

- 基本类型比较值
- 引用类型比较地址

`equals`：

- 默认比较地址
- 很多类会重写，比如 `String`

### 4.2 hashCode 和 equals

约定：

- 两个对象 `equals` 为 `true`，`hashCode` 必须相同
- 两个对象 `hashCode` 相同，`equals` 不一定为 `true`

用途：

- 主要用于 HashMap、HashSet 等哈希集合

## 5. String

### 5.1 String 为什么不可变

原因：

- `String` 底层字符数组被 `final` 修饰
- 类本身也是 `final`
- 没有提供修改内部值的方法

好处：

- 线程安全
- 可以缓存 hashCode
- 可以放入字符串常量池
- 适合做 Map 的 key

### 5.2 StringBuilder 和 StringBuffer

区别：

- `StringBuilder`：线程不安全，性能更好
- `StringBuffer`：线程安全，方法加了 `synchronized`

使用建议：

- 单线程字符串拼接用 `StringBuilder`
- 多线程共享拼接对象时才考虑 `StringBuffer`

## 6. 异常体系

Java 异常顶层是 `Throwable`。

```text
Throwable
├── Error
└── Exception
    ├── RuntimeException
    └── Checked Exception
```

### 6.1 Error

严重错误，通常程序无法处理。

例如：

- `OutOfMemoryError`
- `StackOverflowError`

### 6.2 Exception

程序可以处理的异常。

分为：

- 受检异常：编译期必须处理
- 非受检异常：运行时异常，不强制处理

常见运行时异常：

- `NullPointerException`
- `IndexOutOfBoundsException`
- `ClassCastException`
- `IllegalArgumentException`

### 6.3 异常处理原则

- 不要吞异常
- 不要直接 `catch Exception` 后什么都不做
- 能在当前层处理就处理，不能处理就向上抛
- 业务异常和系统异常要区分
- 日志中保留必要上下文

## 7. 泛型

泛型用于提高代码复用性和类型安全。

```java
List<String> list = new ArrayList<>();
```

优点：

- 编译期类型检查
- 减少强制类型转换
- 提高代码可读性

重点：

- Java 泛型是类型擦除
- 运行时泛型类型信息大多不存在
- 泛型不能直接使用基本类型

## 8. 反射

反射允许程序在运行时获取类的信息，并动态创建对象、调用方法、访问字段。

常见用途：

- Spring IoC
- MyBatis 映射
- 注解解析
- 动态代理
- 测试框架

缺点：

- 性能相对普通调用低
- 破坏封装
- 编译期不容易发现错误

## 9. 注解

注解是给代码添加元数据。

常见注解：

- `@Override`
- `@Deprecated`
- `@SuppressWarnings`
- `@Controller`
- `@Service`
- `@Autowired`
- `@Transactional`

注解本身不直接产生逻辑，通常需要配合反射、AOP、框架解析。

## 10. 集合体系

### 10.1 Collection

```text
Collection
├── List
│   ├── ArrayList
│   └── LinkedList
└── Set
    ├── HashSet
    ├── LinkedHashSet
    └── TreeSet
```

### 10.2 Map

```text
Map
├── HashMap
├── LinkedHashMap
├── TreeMap
├── Hashtable
└── ConcurrentHashMap
```

### 10.3 ArrayList

特点：

- 底层数组
- 查询快
- 插入删除可能慢
- 线程不安全

扩容：

- 默认初始容量通常是 10
- 扩容大约为原来的 1.5 倍

### 10.4 LinkedList

特点：

- 底层双向链表
- 插入删除相对方便
- 随机访问慢

### 10.5 HashMap

JDK 8 结构：

```text
数组 + 链表 + 红黑树
```

put 流程：

```text
计算 hash
定位数组下标
如果桶为空，直接放入
如果桶不为空，判断 key 是否相同
相同则覆盖
不同则链表或红黑树插入
超过阈值触发扩容
链表过长可能转红黑树
```

重点：

- 默认容量 16
- 负载因子 0.75
- 扩容为原来的 2 倍
- 线程不安全

### 10.6 ConcurrentHashMap

JDK 8 结构：

```text
数组 + 链表 + 红黑树 + CAS + synchronized
```

特点：

- 支持并发访问
- 读操作大多无锁
- 写操作通过 CAS 和 synchronized 保证线程安全

## 11. IO 体系

### 11.1 BIO

同步阻塞 IO。

特点：

- 一个连接通常对应一个线程
- 编程简单
- 高并发下线程资源消耗大

### 11.2 NIO

同步非阻塞 IO。

核心组件：

- Buffer
- Channel
- Selector

适合高并发网络通信。

### 11.3 AIO

异步非阻塞 IO。

特点：

- 操作系统完成 IO 后回调应用
- Java 中使用相对少

## 12. 并发编程

### 12.1 线程基础

线程状态：

```text
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

创建线程方式：

- 继承 `Thread`
- 实现 `Runnable`
- 实现 `Callable`
- 使用线程池

### 12.2 synchronized

作用：

- 保证原子性
- 保证可见性
- 保证有序性

锁对象：

- 修饰实例方法：锁当前对象
- 修饰静态方法：锁 Class 对象
- 修饰代码块：锁指定对象

### 12.3 volatile

作用：

- 保证可见性
- 禁止指令重排序

不能保证复合操作原子性。

例如：

```java
count++;
```

不是原子操作。

### 12.4 CAS

CAS 是 Compare And Swap。

包含三个值：

- 内存值
- 期望值
- 新值

优点：

- 无锁
- 性能较好

问题：

- ABA 问题
- 自旋消耗 CPU
- 只能保证单个变量原子操作

### 12.5 线程池

核心参数：

- corePoolSize
- maximumPoolSize
- keepAliveTime
- workQueue
- threadFactory
- rejectedExecutionHandler

执行流程：

```text
核心线程未满 -> 创建核心线程
核心线程已满 -> 进入队列
队列已满 -> 创建非核心线程
线程数达到最大值 -> 执行拒绝策略
```

常见拒绝策略：

- AbortPolicy
- CallerRunsPolicy
- DiscardPolicy
- DiscardOldestPolicy

### 12.6 ThreadLocal

作用：

- 为每个线程保存一份独立变量副本

常见用途：

- 保存用户上下文
- 保存 traceId
- 保存数据库连接

注意：

- 在线程池环境下使用后要及时 `remove`
- 否则可能造成内存泄漏或上下文污染

### 12.7 AQS

AQS 是很多并发工具的基础。

核心思想：

- 一个 state 状态变量
- 一个 FIFO 等待队列
- CAS 修改状态
- 线程阻塞和唤醒

典型应用：

- ReentrantLock
- CountDownLatch
- Semaphore
- ReentrantReadWriteLock

## 13. JVM 基础

### 13.1 JVM 内存结构

```text
线程共享：
  堆
  方法区 / 元空间

线程私有：
  虚拟机栈
  本地方法栈
  程序计数器
```

### 13.2 堆

主要存放对象实例。

GC 主要发生在堆中。

### 13.3 虚拟机栈

每个线程私有。

方法调用会创建栈帧。

栈帧包含：

- 局部变量表
- 操作数栈
- 动态链接
- 方法返回地址

### 13.4 类加载机制

流程：

```text
加载
验证
准备
解析
初始化
```

双亲委派模型：

```text
启动类加载器
扩展类加载器
应用类加载器
自定义类加载器
```

好处：

- 避免类重复加载
- 保护 Java 核心类库安全

### 13.5 GC

判断对象是否可回收：

- 引用计数法
- 可达性分析

Java 主要使用可达性分析。

GC Roots 常见对象：

- 栈中引用的对象
- 静态变量引用的对象
- 常量引用的对象
- 本地方法栈引用的对象

常见垃圾回收器：

- Serial
- ParNew
- Parallel
- CMS
- G1
- ZGC

## 14. Java 8 常用特性

### 14.1 Lambda

用于简化函数式接口写法。

```java
list.forEach(item -> System.out.println(item));
```

### 14.2 Stream

用于集合数据处理。

常见操作：

- filter
- map
- sorted
- distinct
- collect
- reduce

### 14.3 Optional

用于减少显式空判断，但不能滥用。

### 14.4 新时间 API

常用类：

- LocalDate
- LocalTime
- LocalDateTime
- DateTimeFormatter

## 15. 常见设计模式

### 15.1 单例模式

保证一个类只有一个实例。

常见写法：

- 饿汉式
- 懒汉式
- 双重检查锁
- 静态内部类
- 枚举

### 15.2 工厂模式

把对象创建逻辑封装起来。

Spring 中 Bean 创建就体现了工厂思想。

### 15.3 策略模式

把不同算法或业务规则封装成不同策略类。

适合替代大量 `if else`。

### 15.4 代理模式

给目标对象增强功能。

Spring AOP 使用了代理模式。

### 15.5 模板方法模式

父类定义流程，子类实现具体步骤。

## 16. 面试复习顺序建议

建议顺序：

```text
Java 基础
  -> 面向对象
  -> String / Object / 异常
  -> 集合
  -> IO
  -> 并发
  -> JVM
  -> Java 8
  -> 设计模式
```

优先级最高：

- HashMap
- ConcurrentHashMap
- synchronized
- volatile
- 线程池
- ThreadLocal
- JVM 内存结构
- 类加载机制
- GC
- String
- equals 和 hashCode

## 17. 面试回答模板

回答 Java 知识点时，可以按这个结构：

```text
1. 这个东西是什么
2. 它解决什么问题
3. 底层大概怎么实现
4. 常见使用场景
5. 容易踩的坑
6. 项目中如何使用
```

例如回答 HashMap：

```text
HashMap 是 Java 中基于哈希表实现的 Map 集合，主要用于 key-value 存储。
JDK 8 中底层结构是数组、链表和红黑树。
put 时先根据 key 计算 hash，再定位数组下标，如果发生冲突，会用链表或红黑树处理。
它查询效率通常很高，但线程不安全，多线程场景应使用 ConcurrentHashMap。
```

