# Java 基础 1 周学习计划

> 来源材料：`D:\document\facetest\JavaGuide\docs\java\basis`
>
> 目标：用 7 天完成 Java 基础知识的集中复习，优先服务面试表达、代码验证和高频问题查漏补缺。

## 使用方式

- 每天建议投入 2 到 3 小时：先快速通读材料，再挑重点代码验证，最后整理面试回答。
- 每天输出一份小结：包含核心概念、易错点、3 到 5 个面试问答。
- 标星内容优先掌握；底层细节不追求一次性背全，但要能说清用途、原理和常见坑。
- 本计划是压缩版，适合已有 Java 基础的人冲刺复习；如果某天内容明显吃力，优先保证高频面试点。

## 7 天安排

| 天数 | 学习主题 | 对应文件 | 必须掌握 | 输出任务 |
| --- | --- | --- | --- | --- |
| Day 1 | Java 运行机制、基础语法、关键字 | `java-basic-questions-01.md`、`java-keyword-summary.md` | JVM/JDK/JRE、字节码、编译与解释并存、AOT、Java 与 C++ 区别、注释、标识符、关键字、自增自减、移位运算符、`continue`/`break`/`return`、`final`、`static`、`this`、`super` | 画一张 Java 程序运行流程图；整理关键字高频追问 |
| Day 2 | 数据类型、包装类型、BigDecimal、值传递 | `java-basic-questions-01.md`、`bigdecimal.md`、`why-there-only-value-passing-in-java.md` | 8 种基本类型、包装类型区别、缓存机制、自动装箱拆箱、浮点精度问题、`BigDecimal` 使用和比较、Java 只有值传递 | 写代码验证 `Integer` 缓存、自动拆箱空指针、`BigDecimal.equals` 与 `compareTo`、值传递 |
| Day 3 | 面向对象、Object、String | `java-basic-questions-02.md` | 面向对象三大特征、对象引用与实例、构造方法、接口与抽象类、深拷贝/浅拷贝、`==` 与 `equals`、`hashCode`、String 不可变、常量池、`intern`、字符串拼接 | 给一个业务对象重写 `equals`/`hashCode`；画出 `new String("abc")` 的对象关系 |
| Day 4 | 异常、泛型、通配符 | `java-basic-questions-03.md`、`generics-and-wildcards.md` | `Exception` 与 `Error`、Checked/Unchecked、`finally` 执行边界、`try-with-resources`、泛型类/接口/方法、类型擦除、桥方法、`?` 与 `T`、上界下界、PECS | 画 Throwable 体系图；写 `try-with-resources` 示例；写 `extends` 和 `super` 通配符示例 |
| Day 5 | 反射、代理、注解 | `java-basic-questions-03.md`、`reflection.md`、`proxy.md` | `Class` 对象获取方式、反射访问字段/方法/构造器、反射优缺点、静态代理、JDK 动态代理、CGLIB、动态代理在框架中的应用、注解定义和解析方式 | 写一个反射创建对象示例；写一个 JDK 动态代理日志增强示例 |
| Day 6 | SPI、序列化、I/O | `java-basic-questions-03.md`、`spi.md`、`serialization.md` | SPI 与 API 区别、`ServiceLoader`、SPI 优缺点、序列化/反序列化、`transient`、常见序列化协议、JDK 序列化缺点、字节流/字符流、BIO/NIO/AIO | 写最小 SPI Demo；整理 BIO/NIO/AIO 对比表；写 `transient` 序列化示例 |
| Day 7 | 语法糖、Unsafe、总复盘 | `java-basic-questions-03.md`、`syntactic-sugar.md`、`unsafe.md` | 常见语法糖、泛型擦除、自动装箱拆箱、增强 for、try-with-resources、Lambda、Unsafe 创建方式、内存操作、CAS、内存屏障、线程调度、Class 操作 | 用 `javap -c` 反编译 3 个语法糖示例；整理 50 题自测清单并标记薄弱点 |

## 每天复习节奏

| 时间段 | 动作 | 说明 |
| --- | --- | --- |
| 第 1 阶段 | 快速阅读 | 先扫标题和标星问题，再读正文，抓结论和例子 |
| 第 2 阶段 | 代码验证 | 每天至少写 2 到 3 个小示例，验证容易混淆的点 |
| 第 3 阶段 | 面试表达 | 把当天主题整理成自己的回答，不要只摘抄原文 |
| 第 4 阶段 | 错题复盘 | 记录答不出来、说不清、容易写错的点 |

## 高频优先级

| 优先级 | 模块 | 必背问题 |
| --- | --- | --- |
| P0 | Java 运行机制 | JVM/JDK/JRE 区别、字节码好处、编译与解释并存 |
| P0 | 数据类型 | 基本类型与包装类型、缓存机制、自动装箱拆箱、浮点精度、`BigDecimal` |
| P0 | 面向对象 | 三大特征、接口和抽象类、重载和重写、值传递 |
| P0 | Object/String | `equals`/`hashCode`、`==`、String 不可变、常量池、`intern` |
| P0 | 异常 | Checked/Unchecked、`finally`、`try-with-resources`、异常使用规范 |
| P1 | 泛型 | 类型擦除、桥方法、通配符、PECS |
| P1 | 反射代理 | 反射优缺点、JDK 动态代理、CGLIB、框架应用场景 |
| P1 | SPI/序列化/I/O | SPI 与 API、`ServiceLoader`、序列化协议、BIO/NIO/AIO |
| P2 | 语法糖/Unsafe | 语法糖反编译、自动装箱坑、Unsafe、CAS、内存屏障 |

## 7 天验收标准

- 能闭卷回答至少 50 个 Java 基础高频问题。
- 能写出关键代码示例：`equals`/`hashCode`、`BigDecimal`、值传递、泛型通配符、反射、JDK 动态代理、SPI、序列化。
- 能解释常见坑：包装类型缓存、自动拆箱空指针、浮点精度、String 常量池、泛型擦除、增强 for 删除元素、JDK 序列化缺点。
- 能把知识点和项目场景关联起来：金额计算用 `BigDecimal`，框架使用反射和代理，扩展点使用 SPI，集合和通用返回对象使用泛型。

## 最后一天自测清单

- JVM、JDK、JRE 分别是什么？
- 什么是字节码？为什么 Java 使用字节码？
- Java 为什么说是编译与解释并存？
- 基本类型和包装类型有什么区别？
- `Integer` 缓存机制是什么？
- 自动装箱和拆箱可能带来什么问题？
- 浮点数为什么会有精度问题？如何解决？
- `BigDecimal` 为什么推荐用字符串构造？
- `BigDecimal.equals` 和 `compareTo` 有什么区别？
- Java 为什么只有值传递？
- `final`、`static`、`this`、`super` 分别怎么用？
- 重载和重写有什么区别？
- 接口和抽象类有什么区别？
- 深拷贝和浅拷贝有什么区别？
- `==` 和 `equals` 有什么区别？
- 为什么重写 `equals` 必须重写 `hashCode`？
- String 为什么不可变？
- 字符串常量池有什么作用？
- `new String("abc")` 创建几个对象？
- `intern` 方法有什么作用？
- `Exception` 和 `Error` 有什么区别？
- Checked Exception 和 Unchecked Exception 有什么区别？
- `finally` 一定会执行吗？
- `try-with-resources` 的优势是什么？
- 泛型有什么作用？
- 什么是类型擦除？
- 什么是桥方法？
- `?` 和 `T` 有什么区别？
- `extends` 和 `super` 通配符怎么用？
- 什么是反射？优缺点是什么？
- 反射有哪些应用场景？
- 静态代理和动态代理有什么区别？
- JDK 动态代理和 CGLIB 有什么区别？
- 动态代理在 Spring 或 MyBatis 中有什么应用？
- 注解是什么？如何解析？
- SPI 和 API 有什么区别？
- `ServiceLoader` 的基本原理是什么？
- 什么是序列化和反序列化？
- `transient` 有什么作用？
- 为什么不推荐 JDK 自带序列化？
- 字节流和字符流有什么区别？
- BIO、NIO、AIO 有什么区别？
- 什么是语法糖？
- Java 常见语法糖有哪些？
- 自动装箱拆箱有什么坑？
- 增强 for 循环删除元素有什么问题？
- Unsafe 是什么？
- Unsafe 的 CAS 操作有什么作用？
- 内存屏障是什么？
- 哪些 Java 基础知识能和你的项目经历关联起来？
