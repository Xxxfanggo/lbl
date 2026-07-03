# Java 后端面试 2 个月复习大纲

## 复习目标

用 8 周完成 Java 后端面试核心知识体系复习，覆盖 Java 基础、集合、并发、IO、数据库、Redis、常用框架、系统设计、分布式等内容。每周安排一次总结复习，第 4 周和第 8 周安排月度大总结复习。

## 每周固定节奏

- 周一至周五：主线知识学习 + 面试题整理 + 代码/案例理解
- 周六：本周总结复习，输出知识脑图、错题清单、重点问题答案
- 周日：查漏补缺 + 模拟问答 + 下周预习

## 每日建议安排

- 30 分钟：回顾前一天内容
- 90 至 120 分钟：学习当天主线内容
- 30 至 60 分钟：整理面试题答案
- 30 分钟：用自己的话复述核心知识点

## 第 1 周：Java 基础与面向对象

### 学习重点

- Java 知识体系整体梳理
- Java 程序运行流程：编译、类加载、解释执行、JIT
- 基本数据类型、包装类型、自动拆装箱
- String、StringBuilder、StringBuffer
- final、static、this、super、访问修饰符
- 面向对象三大特性：封装、继承、多态
- 抽象类与接口
- Object 常见方法：equals、hashCode、toString、clone
- 异常体系：受检异常、非受检异常、异常处理原则
- 泛型、反射、注解、枚举

### 面试题方向

- == 和 equals 的区别
- hashCode 和 equals 为什么要一起重写
- String 为什么不可变
- StringBuilder 和 StringBuffer 的区别
- 接口和抽象类怎么选择
- Java 异常体系如何设计
- 反射的使用场景和缺点

### 周总结复习

- 画出 Java 基础知识脑图
- 整理 20 道 Java 基础高频面试题
- 对 String、equals/hashCode、异常、反射做重点复述
- 标记仍然说不清楚的概念，放入下周回顾清单

## 第 2 周：集合、源码与 Java IO

### 学习重点

- Java 集合体系总览
- List、Set、Map 的区别和使用场景
- ArrayList、LinkedList、Vector
- HashMap、LinkedHashMap、TreeMap、ConcurrentHashMap
- HashSet、LinkedHashSet、TreeSet
- HashMap 源码：数组、链表、红黑树、扩容、扰动函数
- ConcurrentHashMap 源码与线程安全设计
- fail-fast 与 fail-safe
- Java IO 基础：BIO、NIO、AIO
- NIO 核心：Buffer、Channel、Selector
- 零拷贝、文件传输、网络 IO 模型
- 常见设计模式：单例、工厂、策略、模板、代理、观察者

### 面试题方向

- HashMap 的 put 流程
- HashMap 为什么线程不安全
- HashMap 和 ConcurrentHashMap 的区别
- ArrayList 和 LinkedList 如何选择
- 红黑树引入的目的是什么
- BIO、NIO、AIO 的区别
- select、poll、epoll 的区别
- 设计模式在 Spring 中的应用

### 周总结复习

- 手写或口述 HashMap put 流程
- 总结集合类选择表：场景、特点、复杂度、线程安全性
- 整理 IO 模型对比表
- 用项目经验串联 2 至 3 个设计模式

## 第 3 周：Java 并发编程

### 学习重点

- 线程基础：线程状态、创建方式、生命周期
- synchronized 原理、锁升级、偏向锁、轻量级锁、重量级锁
- volatile 原理：可见性、有序性、禁止指令重排
- Java 内存模型 JMM
- happens-before 规则
- CAS、ABA 问题、Atomic 原子类
- ReentrantLock、读写锁、Condition
- 线程池核心参数、执行流程、拒绝策略
- ThreadLocal 原理与内存泄漏
- AQS 原理
- CountDownLatch、CyclicBarrier、Semaphore
- CompletableFuture 和异步编排

### 面试题方向

- synchronized 和 ReentrantLock 的区别
- volatile 能不能保证原子性
- 线程池为什么不建议用 Executors
- ThreadLocal 为什么可能内存泄漏
- AQS 是什么，核心思想是什么
- CAS 的优缺点
- 如何排查死锁

### 周总结复习

- 画出线程池执行流程图
- 整理 synchronized、volatile、CAS、AQS 四个核心专题
- 准备一个并发问题排查案例
- 模拟回答 15 道并发高频面试题

## 第 4 周：MySQL、SQL、Redis 与第 1 个月大总结

### 学习重点

- 数据库知识体系总览
- SQL 基础：增删改查、连接查询、子查询、聚合、排序、分页
- 索引基础：B+ 树、聚簇索引、非聚簇索引、联合索引
- MySQL 执行计划 explain
- 索引失效场景
- 事务 ACID
- 隔离级别与 MVCC
- redo log、undo log、binlog
- MySQL 锁：行锁、表锁、间隙锁、临键锁
- 慢 SQL 排查与优化
- Redis 数据类型与使用场景
- Redis 持久化：RDB、AOF
- Redis 过期删除、内存淘汰策略
- 缓存穿透、缓存击穿、缓存雪崩
- Redis 分布式锁基础

### 面试题方向

- MySQL 为什么使用 B+ 树
- 什么情况下索引会失效
- MVCC 如何实现
- redo log、undo log、binlog 的区别
- 可重复读能解决幻读吗
- Redis 常见数据类型适合哪些场景
- 缓存穿透、击穿、雪崩如何解决
- Redis 分布式锁需要注意什么

### 周总结复习

- 输出 MySQL 核心知识脑图
- 整理一份 SQL 优化检查清单
- 总结 Redis 缓存问题解决方案
- 完成 MySQL + Redis 模拟面试

### 第 1 个月大总结复习

- 回顾第 1 至第 4 周所有错题和薄弱点
- 按模块整理：Java 基础、集合、并发、IO、MySQL、Redis
- 每个模块输出 5 个必须会讲清楚的问题
- 做一次 60 至 90 分钟模拟面试
- 建立第 2 个月重点清单：框架、系统设计、分布式、项目表达

## 第 5 周：Spring、Spring MVC、Spring Boot、MyBatis

### 学习重点

- Spring 核心思想：IoC、DI、AOP
- Bean 生命周期
- BeanFactory 和 ApplicationContext
- 循环依赖解决过程
- Spring 事务传播行为与失效场景
- AOP 动态代理：JDK 代理、CGLIB
- Spring MVC 请求处理流程
- HandlerMapping、HandlerAdapter、DispatcherServlet
- Spring Boot 自动配置原理
- starter 机制
- 配置加载顺序
- MyBatis 执行流程
- #{} 和 ${} 的区别
- 一级缓存、二级缓存
- MyBatis 插件机制

### 面试题方向

- Spring Bean 生命周期
- Spring 如何解决循环依赖
- Spring 事务为什么会失效
- Spring MVC 请求流程
- Spring Boot 自动配置原理
- MyBatis 如何防止 SQL 注入
- MyBatis 一级缓存和二级缓存区别

### 周总结复习

- 画出 Spring Bean 生命周期图
- 画出 Spring MVC 请求流程图
- 整理 Spring 事务失效场景清单
- 准备一个 Spring Boot 自动配置的口述版本

## 第 6 周：系统设计基础、认证授权、数据安全与消息推送

### 学习重点

- 系统设计知识体系
- 高可用、高并发、高性能、可扩展基本概念
- 常见架构分层：网关层、业务层、缓存层、数据层
- 接口设计原则：幂等、限流、降级、熔断、重试
- 认证授权：Cookie、Session、Token、JWT、OAuth2
- 单点登录 SSO
- 数据安全：加密、脱敏、权限控制、防重放
- 常见 Web 安全：XSS、CSRF、SQL 注入
- Java 定时任务：Timer、ScheduledExecutorService、Spring Task、XXL-JOB
- Web 实时消息推送：轮询、长轮询、SSE、WebSocket
- 系统设计常见面试题总结

### 面试题方向

- JWT 和 Session 的区别
- 如何设计登录态
- 如何保证接口幂等
- 限流有哪些实现方式
- 如何设计一个定时任务系统
- WebSocket 和 SSE 的区别
- 如何防止重复提交

### 周总结复习

- 整理一份系统设计通用回答模板
- 总结认证授权方案对比表
- 输出接口安全与数据安全检查清单
- 选择一个业务场景做小型系统设计练习

## 第 7 周：分布式核心专题

### 学习重点

- 分布式系统知识体系
- CAP、BASE、最终一致性
- 分布式系统入门：服务拆分、远程调用、注册发现
- API 网关：路由、鉴权、限流、灰度发布
- Spring Cloud Gateway 面试重点
- 分布式 ID：UUID、数据库号段、Redis、自增雪花算法
- 分布式锁：Redis、Redisson、ZooKeeper、数据库
- 分布式事务：2PC、TCC、本地消息表、可靠消息、Saga
- 分布式配置中心：配置管理、动态刷新、灰度配置
- RPC 基础：调用流程、序列化、负载均衡、超时重试
- Netty 基础：EventLoop、Channel、ByteBuf、零拷贝

### 面试题方向

- CAP 和 BASE 如何理解
- 分布式 ID 如何设计
- Redis 分布式锁有什么问题
- Redisson 看门狗机制是什么
- 分布式事务有哪些解决方案
- API 网关的作用是什么
- RPC 和 HTTP 调用有什么区别
- Netty 为什么性能高

### 周总结复习

- 整理分布式专题知识脑图
- 对比 4 种分布式事务方案
- 准备分布式锁和分布式 ID 的项目化表达
- 做一次分布式专题模拟面试

## 第 8 周：NoSQL、Elasticsearch、MongoDB、综合冲刺与第 2 个月大总结

### 学习重点

- NoSQL 基础与适用场景
- 字符集、编码、排序规则基础
- Elasticsearch 基础：倒排索引、分词、文档、索引、查询
- Elasticsearch 写入和查询流程
- Elasticsearch 与 MySQL 的使用边界
- MongoDB 基础：文档模型、集合、索引、适用场景
- 综合复习：Java、数据库、框架、系统设计、分布式
- 项目复盘：项目背景、技术选型、难点、优化、故障处理
- 高频面试题二轮复盘
- 自我介绍与项目介绍打磨

### 面试题方向

- Elasticsearch 为什么适合全文检索
- 倒排索引是什么
- Elasticsearch 写入数据的大致流程
- MongoDB 适合什么场景
- 如何介绍自己的项目
- 项目中遇到过哪些难点
- 如何做性能优化
- 如何排查线上问题

### 周总结复习

- 整理 NoSQL、Elasticsearch、MongoDB 对比表
- 完成项目介绍稿
- 整理最终版高频面试题清单
- 做一次全链路模拟面试

### 第 2 个月大总结复习

- 复盘第 5 至第 8 周内容
- 按面试模块输出最终知识清单：
  - Java 基础与集合
  - 并发编程
  - JVM 与 IO
  - MySQL 与 Redis
  - Spring、Spring Boot、MyBatis
  - 系统设计
  - 分布式
  - 项目经验
- 每个模块准备 3 至 5 个高质量回答
- 做 2 轮模拟面试：一轮基础八股，一轮项目 + 系统设计
- 整理最终薄弱点，只保留 10 个最需要补的问题

## 每周总结模板

```text
第 X 周总结

1. 本周完成内容：

2. 本周掌握最好的 5 个知识点：

3. 本周仍然薄弱的 5 个问题：

4. 高频面试题整理：

5. 可以结合项目讲的知识点：

6. 下周需要优先复习：
```

## 月度大总结模板

```text
第 X 个月大总结

1. 本月完成模块：

2. 最重要的 10 个面试题：

3. 最容易混淆的知识点：

4. 已经能流畅表达的内容：

5. 仍然需要二刷的内容：

6. 模拟面试暴露的问题：

7. 下个月或最终冲刺计划：
```

## 最终冲刺建议

- 每天至少口述 5 道面试题，重点训练表达，而不是只看答案
- 每个知识点都尽量回答到：是什么、为什么、怎么用、有什么坑、项目里怎么体现
- 对源码类问题不追求背代码，重点讲清楚核心流程和设计思想
- 对系统设计和分布式问题，优先建立通用分析框架
- 项目经验要和技术点绑定，避免只描述业务流程

