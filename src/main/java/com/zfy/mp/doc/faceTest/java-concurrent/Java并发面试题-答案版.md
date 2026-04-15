# Java并发面试题 - 答案版

> 来源：JavaGuide (javaguide.cn)
> 整理：Claude

---

## 线程基础

### 1. 什么是线程和进程？

- **进程**：程序的一次执行过程，是系统运行程序的基本单位。进程是动态的，有创建、运行、消亡的过程。
- **线程**：比进程更小的执行单位，一个进程在其执行过程中可以产生多个线程。同类线程共享进程的**堆**和**方法区**资源，但每个线程有自己的**程序计数器**、**虚拟机栈**和**本地方法栈**。

---

### 2. Java 线程和操作系统的线程有什么区别？

JDK 1.2 之前，Java 线程基于绿色线程（用户级线程）实现。JDK 1.2 及以后，Java 线程改为基于原生线程实现，直接使用操作系统内核线程。

现在的 Java 线程的本质就是操作系统的线程。主流操作系统（Windows、Linux）采用一对一线程模型。

---

### 3. 线程与进程的区别、优缺点？

**区别**：
- 进程是独立的，线程不一定独立（同一进程的线程可能相互影响）
- 线程执行开销小，但不利于资源管理；进程正相反

**线程私有资源**：程序计数器、虚拟机栈、本地方法栈
**线程共享资源**：堆、方法区

---

### 4. 程序计数器为什么是私有的？

1. 字节码解释器通过改变程序计数器来执行代码流程控制
2. 多线程环境下，程序计数器记录当前线程执行位置，线程切换后能恢复到正确位置

---

### 5. 虚拟机栈和本地方法栈为什么是私有的？

- **虚拟机栈**：每个 Java 方法执行时会创建栈帧，存储局部变量等信息。私有是为了保证线程中局部变量不被其他线程访问。
- **本地方法栈**：为虚拟机使用到的 Native 方法服务，与虚拟机栈类似。

---

### 6. 如何创建线程？

Java 只有一种方式创建线程：`new Thread().start()`。其他方式（如实现 Runnable、Callable、使用线程池）最终都依赖这种方式。

---

### 7. 线程的生命周期和状态有哪些？

Java 线程的 6 种状态：
- **NEW**：初始状态，线程被创建但未调用 start()
- **RUNNABLE**：运行状态，调用 start() 后等待运行
- **BLOCKED**：阻塞状态，需要等待锁释放
- **WAITING**：等待状态，需要等待其他线程通知
- **TIME_WAITING**：超时等待状态，可在指定时间后自行返回
- **TERMINATED**：终止状态，线程已执行完毕

---

### 8. 什么是线程上下文切换？

线程在执行过程中有自己的运行条件和状态。当线程主动让出 CPU、时间片用完、调用阻塞类型系统中断、被终止或结束时会退出 CPU。

保存当前线程上下文，加载下一个线程上下文的过程就是**上下文切换**。上下文切换是现代操作系统基本功能，会有性能损耗。

---

### 9. Thread#sleep() 和 Object#wait() 的区别？

| 区别 | sleep() | wait() |
|------|----------|--------|
| 释放锁 | 不释放 | 释放 |
| 用途 | 暂停执行 | 线程间交互/通信 |
| 自动苏醒 | 是（时间到） | 否（需 notify/notifyAll） |
| 所属 | Thread 类的静态方法 | Object 类的本地方法 |

---

### 10. 为什么 wait() 方法不定义在 Thread 中？

wait() 是让获得对象锁的线程实现等待，会自动释放当前线程占有的对象锁。既然要操作对应的对象，自然要在 Object 中定义。

---

### 11. 可以直接调用 Thread 类的 run 方法吗？

可以调用，但不会以多线程方式执行。必须调用 `start()` 方法才能启动线程并使线程进入就绪状态。

---

## 多线程基础

### 12. 并发与并行的区别？

- **并发**：两个及以上的作业在同一**时间段**内执行
- **并行**：两个及以上的作业在同一**时刻**执行

关键点是是否**同时**执行。

---

### 13. 同步和异步的区别？

- **同步**：调用在得到结果之前不会返回，一直等待
- **异步**：调用直接返回，不需要等待结果

---

### 14. 为什么要使用多线程？

- **计算机底层**：线程切换成本低，多核 CPU 可同时运行减少上下文切换开销
- **互联网趋势**：高并发需求，多线程并发编程是基础

**单核时代**：多线程提高 CPU 和 IO 系统利用率
**多核时代**：多线程提高多核 CPU 利用率

---

### 15. 单核 CPU 支持 Java 多线程吗？

支持。操作系统通过时间片轮转方式分配 CPU 时间给不同线程，快速切换让用户感觉多个任务同时进行。

Java 使用抢占式调度，JVM 将线程调度委托给操作系统。

---

### 16. 单核 CPU 上运行多个线程效率一定会高吗？

不一定，取决于任务类型：
- **CPU 密集型**：多线程会导致频繁切换，降低效率
- **IO 密集型**：多线程可利用 IO 等待时的空闲时间，提高效率

---

### 17. 使用多线程可能带来什么问题？

- 内存泄漏
- 死锁
- 线程不安全
- 上下文切换开销

---

### 18. 如何理解线程安全和不安全？

- **线程安全**：多线程环境下，对同一份数据访问能保证正确性和一致性
- **线程不安全**：多线程同时访问可能导致数据混乱、错误或丢失

---

## 死锁

### 19. 什么是线程死锁？

多个线程同时被阻塞，它们都在等待某个资源被释放，由于线程被无限期阻塞，程序无法正常终止。

---

### 20. 产生死锁的四个必要条件？

1. **互斥条件**：资源任意时刻只能被一个线程占用
2. **请求与保持条件**：线程因请求资源阻塞时，对已获得的资源保持不放
3. **不剥夺条件**：线程已获得的资源不能被强行剥夺
4. **循环等待条件**：若干线程形成头尾相接的循环等待关系

---

### 21. 如何检测死锁？

- 使用 `jstack`、`jmap` 等命令查看 JVM 线程栈和堆内存，`jstack` 输出中有 `Found one Java-level deadlock:` 字样
- 使用 VisualVM、JConsole 等工具排查

---

### 22. 如何预防和避免线程死锁？

**预防**（破坏死锁必要条件）：
- 破坏请求与保持条件：一次性申请所有资源
- 破坏不剥夺条件：主动释放占有的资源
- 破坏循环等待条件：按序申请资源

**避免**：使用银行家算法对资源分配进行计算评估，使其进入安全状态。

---

## JMM与volatile

### 23. JMM（Java 内存模型）是什么？

JMM（Java Memory Model）是一种规范，定义了 JVM 在计算机内存中的工作方式，屏蔽了不同硬件和操作系统的内存访问差异。

---

### 24. volatile 如何保证变量的可见性？

volatile 关键字指示 JVM 这个变量是共享且不稳定的，每次使用都到主存中进行读取。

---

### 25. volatile 如何禁止指令重排序？

volatile 关键字通过插入特定的**内存屏障**来禁止指令重排序。

---

### 26. 什么是内存屏障？有哪几种类型？

| 屏障类型 | 说明 |
|----------|------|
| LoadLoad | 保证 Load1 在 Load2 及其后续读取之前完成 |
| StoreStore | 保证 Store1 在 Store2 及其后续写入之前完成 |
| LoadStore | 保证 Load1 在 Store2 及其后续写入之前完成 |
| StoreLoad | 保证 Store1 在 Load2 及其后续读取之前完成（最全能的屏障） |

---

### 27. volatile 读写操作的内存屏障插入策略？

**volatile 写操作**：
- 前面插入 StoreStore 屏障
- 后面插入 StoreLoad 屏障

**volatile 读操作**：
- 后面插入 LoadLoad 屏障
- 后面插入 LoadStore 屏障

---

### 28. volatile 与 happens-before 的关系？

happens-before 原则中与 volatile 直接相关的是 **volatile 变量规则**：

> 对一个 volatile 变量的写操作 happens-before 后续对该 volatile 变量的读操作。

这意味着 volatile 变量的写-读不仅保证了自身可见性，还通过 happens-before 传递性保证了其前后普通变量的可见性。

---

### 29. volatile 可以保证原子性么？

**不能保证原子性**。

`inc++` 是复合操作（读取→加1→写回），volatile 无法保证这三步的原子性。

可以使用 `synchronized`、`Lock` 或 `AtomicInteger` 来保证原子性。

---

## 乐观锁和悲观锁

### 30. 什么是悲观锁？

总是假设最坏情况，认为共享资源每次被访问都会出问题，所以每次获取资源操作都上锁。导致其他线程阻塞。

代表：`synchronized`、`ReentrantLock`

---

### 31. 什么是乐观锁？

假设最好情况，认为共享资源不会被修改，只在提交修改时验证（版本号或 CAS）。

代表：`AtomicInteger`、`LongAdder`（CAS 实现）

---

### 32. 如何实现乐观锁？

**版本号机制**：数据表加 `version` 字段，提交时比较版本号
**CAS 算法**：比较并交换，预期值等于当前值时才更新

---

### 33. CAS 算法是什么？

CAS（Compare-And-Swap）包含三个操作数：
- **V**：要更新的变量值
- **E**：预期值
- **N**：拟写入的新值

当 V=E 时，才会将 V 更新为 N，否则放弃更新。

---

### 34. Java 中 CAS 是如何实现的？

Java 通过 `Unsafe` 类提供 CAS 方法（`compareAndSwapObject`、`compareAndSwapInt`、`compareAndSwapLong`），这些是 native 方法，底层依赖 CPU 原子指令。

`AtomicInteger` 等原子类利用 `Unsafe` 类实现无锁线程安全。

---

### 35. CAS 算法存在哪些问题？

**ABA 问题**：变量从 A 改为 B 再改回 A，CAS 认为没变化。解决：使用 `AtomicStampedReference`（带版本号）

**循环时间长开销大**：自旋失败后会一直重试。解决：JDK 提供 pause 指令优化

**只能保证一个共享变量**：解决：使用 `AtomicReference` 包装多个变量

---

### 36. 乐观锁和悲观锁的区别？

| 对比维度 | 乐观锁 | 悲观锁 |
|----------|--------|--------|
| 核心假设 | 冲突很少发生 | 冲突必然发生 |
| 底层原理 | CAS 或版本号机制 | 操作系统互斥锁 |
| 阻塞情况 | 非阻塞 | 阻塞 |
| 并发开销 | CPU消耗（自旋重试） | 上下文切换 |
| 死锁风险 | 无 | 有 |
| 适用场景 | 多读少写 | 多写少读 |

---

## synchronized

### 37. synchronized 是什么？有什么用？

`synchronized` 是 Java 关键字，用于解决多个线程之间访问资源的同步性，保证任意时刻只有一个线程执行被修饰的方法或代码块。

早期是重量级锁，Java 6 后引入了大量优化（自旋锁、偏向锁、轻量级锁等）。

---

### 38. 如何使用 synchronized？

1. **修饰实例方法**：锁当前对象实例
2. **修饰静态方法**：锁当前类
3. **修饰代码块**：锁指定对象/类

```java
synchronized void method() {} // 锁当前实例
synchronized static void method() {} // 锁类
synchronized(this) {} // 锁对象
synchronized(类.class) {} // 锁类
```

---

### 39. 构造方法可以用 synchronized 修饰么？

不能，但可以在构造方法内部使用 synchronized 代码块。

---

### 40. synchronized 底层原理是什么？

synchronized 底层原理属于 JVM 层面，通过 `monitorenter` 和 `monitorexit` 指令（或 `ACC_SYNCHRONIZED` 标识）实现，本质都是对对象监视器 monitor 的获取。

---

### 41. synchronized 同步语句块的情况？

使用 `monitorenter` 和 `monitorexit` 指令：
- `monitorenter` 指向同步代码块开始位置
- `monitorexit` 指明同步代码块结束位置

获取锁时计数器加 1，释放锁时计数器减 1。

---

### 42. synchronized 修饰方法的情况？

使用 `ACC_SYNCHRONIZED` 标识，指明该方法是同步方法。JVM 通过该标识辨别并执行相应的同步调用。

---

### 43. JDK1.6 之后的 synchronized 做了哪些优化？

引入了**偏向锁、轻量级锁、自旋锁、适应性自旋锁、锁消除、锁粗化**等技术。

**锁升级（不可降级）**：无锁 → 偏向锁 → 轻量级锁 → 重量级锁

---

### 44. synchronized 的偏向锁为什么被废弃？

- **性能收益不明显**：只在单线程访问同步代码块时有收益
- **撤销成本高**：需要等待进入全局安全点
- **维护成本高**：增加了 JVM 内部代码复杂性

JDK 15 默认关闭，JDK 18 彻底废弃。

---

### 45. synchronized 和 volatile 有什么区别？

| 区别 | synchronized | volatile |
|------|-------------|----------|
| 性能 | 更低 | 更高（轻量级） |
| 修饰范围 | 方法/代码块 | 只能修饰变量 |
| 原子性 | 保证 | 不保证 |
| 可见性 | 保证 | 保证 |
| 有序性 | 保证 | 保证（禁止重排序） |

---

## ReentrantLock

### 46. ReentrantLock 是什么？

实现 `Lock` 接口的可重入独占锁，比 `synchronized` 更灵活，支持公平锁/非公平锁、等待中断、超时、公平锁等高级功能。

内部通过 AQS（AbstractQueuedSynchronizer）实现。

---

### 47. 公平锁和非公平锁有什么区别？

- **公平锁**：先申请的线程先获得锁，效率较低但保证顺序
- **非公平锁**：后申请的线程可能先获得，效率更高但可能饥饿

ReentrantLock 默认非公平锁，可通过构造方法指定。

---

### 48. synchronized 和 ReentrantLock 有什么区别？

| 区别 | synchronized | ReentrantLock |
|------|-------------|---------------|
| 底层实现 | JVM 实现 | API 实现 |
| 锁类型 | 非公平锁 | 公平/非公平 |
| 等待可中断 | 否 | 支持 |
| 公平锁 | 否 | 支持 |
| 条件通知 | 一个条件变量 | 多个 Condition |
| 锁超时 | 否 | 支持 tryLock() |

---

### 49. 可重入锁是什么？

可重入锁（递归锁）指线程可以再次获取自己的内部锁。`synchronized` 和 `ReentrantLock` 都是可重入锁。

---

### 50. ReentrantLock 相比 synchronized 增加了哪些高级功能？

- **等待可中断**：`lock.lockInterruptibly()`
- **公平锁**：`ReentrantLock(boolean fair)`
- **多条件通知**：`Condition` 分组唤醒
- **超时**：`tryLock(timeout)`

---

### 51. 可中断锁和不可中断锁的区别？

- **不可中断锁**：等待期间收到中断信号仍继续等待，直到获得锁
  - `synchronized`、`ReentrantLock.lock()`
- **可中断锁**：等待期间可响应中断，抛出 InterruptedException
  - `ReentrantLock.lockInterruptibly()`、`tryLock(timeout)`

---

## ReentrantReadWriteLock

### 52. ReentrantReadWriteLock 是什么？

实现 `ReadWriteLock` 接口的可重入读写锁，读锁共享，写锁独占。

- 读读不互斥
- 读写互斥
- 写写互斥

适合**读多写少**场景。

---

### 53. ReentrantReadWriteLock 适合什么场景？

读多写少的场景，可以显著提升系统性能。

---

### 54. 共享锁和独占锁的区别？

- **共享锁**：可被多个线程同时获得
- **独占锁**：只能被一个线程获得

---

### 55. 线程持有读锁还能获取写锁吗？

不能。持有读锁时获取写锁会失败。
持有写锁时可以获取读锁（锁降级）。

---

### 56. 读锁为什么不能升级为写锁？

因为读锁升级为写锁会引起线程争夺，可能导致死锁。两个线程都想升级读锁，都需要对方释放锁，互相等待。

---

## StampedLock

### 57. StampedLock 是什么？

JDK 1.8 引入的性能更好的读写锁，基于 CLH 锁实现。不可重入，不支持 `Condition`。

三种模式：
- **写锁**：独占锁
- **读锁（悲观读）**：共享锁
- **乐观读**：无写锁时直接访问，提交时验证

---

### 58. StampedLock 有哪几种模式？

- **写锁**：独占锁，阻塞其他读写
- **读锁**：共享锁，无写锁时多线程共享
- **乐观读**：不阻塞写线程，减少线程饥饿

---

### 59. StampedLock 的性能为什么更好？

乐观读允许在有写锁时通过验证来减少阻塞，吞吐量大大提升，适合读多写少场景。

---

### 60. StampedLock 适合什么场景？

读多写少的业务场景，可替代 `ReentrantReadWriteLock`，但需要注意不可重入、不支持 Condition、使用不当易导致 CPU 飙升。

---

## ThreadLocal

### 61. ThreadLocal 有什么用？

让每个线程拥有自己的专属本地变量，绑定到自己的值。通过 `get()` 和 `set()` 方法访问，互不干扰。

---

### 62. ThreadLocal 原理了解吗？

每个 Thread 对象内部有 `ThreadLocalMap`，是 ThreadLocal 的静态内部类实现的定制化 HashMap。

- `Thread` → `threadLocals`（ThreadLocalMap）
- `ThreadLocalMap` 的 key 是 ThreadLocal 实例，value 是设置的值
- 最终变量存储在当前线程的 `ThreadLocalMap` 中

---

### 63. ThreadLocal 内存泄露是怎么导致的？

`ThreadLocalMap` 的 Entry 继承 `WeakReference<ThreadLocal<?>>`，key 是弱引用。但 value 是强引用。

当 ThreadLocal 实例失去强引用后被 GC 回收，key 变为 null，但 value 无法回收。如果线程持续存活（如线程池），会造成内存泄漏。

---

### 64. 为什么 Entry 的 key 设计为弱引用？

弱引用是一种"兜底防御"机制——即使开发者忘记调用 `remove()`，JVM GC 配合 `ThreadLocalMap` 的自清理逻辑仍有机会回收泄漏数据。

如果用强引用，ThreadLocal 和 value 都无法回收。

---

### 65. 如何避免 ThreadLocal 内存泄露？

**必须调用 `remove()` 方法**。建议使用 `try-finally` 块确保清理：

```java
try {
    threadLocal.set(value);
    // 业务代码
} finally {
    threadLocal.remove();
}
```

---

### 66. 线程池场景下 ThreadLocal 有什么特殊风险？

- **内存泄漏持续累积**：核心线程不复活，value 不断累积
- **数据污染**：上一个任务的 ThreadLocal 值残留到下一个任务

阿里巴巴 Java 开发手册强制要求：必须回收自定义 ThreadLocal 变量。

---

### 67. 如何跨线程传递 ThreadLocal 的值？

**方案一：InheritableThreadLocal**
- 子线程继承父线程的 ThreadLocal 值
- 只在线程创建时复制一次，线程池场景失效

**方案二：TransmittableThreadLocal（TTL）**
- 阿里巴巴开源，在线程池场景下完美支持
- 原理：提交任务时 Capture → 执行前 Replay → 执行后 Restore

---

## 线程池

### 68. 什么是线程池？

管理线程资源的池子，线程完成任务后不销毁，等待下一个任务，提高资源利用率和响应速度。

---

### 69. 为什么要用线程池？

1. **降低资源消耗**：线程复用，避免频繁创建销毁
2. **提高响应速度**：核心线程可直接执行任务
3. **提高可管理性**：统一管理线程，控制并发数量

---

### 70. 如何创建线程池？

**方式一（推荐）**：`ThreadPoolExecutor` 构造函数直接创建

**方式二**：`Executors` 工具类创建（不推荐生产环境）

---

### 71. 为什么不推荐使用内置线程池？

- `FixedThreadPool`、`SingleThreadExecutor`：队列无限大，可能 OOM
- `CachedThreadPool`：线程数无限大，可能 OOM
- `ScheduledThreadPool`：队列无限大，可能 OOM

---

### 72. 线程池常见参数有哪些？

| 参数 | 说明 |
|------|------|
| corePoolSize | 核心线程数量 |
| maximumPoolSize | 最大线程数 |
| keepAliveTime | 非核心线程空闲存活时间 |
| unit | 时间单位 |
| workQueue | 任务队列 |
| threadFactory | 线程工厂 |
| handler | 拒绝策略 |

---

### 73. 线程池的核心线程会被回收吗？

默认不会回收。如果调用 `allowCoreThreadTimeOut(true)`，核心线程也会被回收。

---

### 74. 核心线程空闲时处于什么状态？

- **设置了存活时间**：处于 WAITING 超时等待，超时后转为 TERMINATED
- **未设置**：一直处于 WAITING，等待新任务

---

### 75. 线程池的拒绝策略有哪些？

| 策略 | 行为 |
|------|------|
| AbortPolicy | 抛出 RejectedExecutionException |
| CallerRunsPolicy | 调用者线程执行任务 |
| DiscardPolicy | 直接丢弃 |
| DiscardOldestPolicy | 丢弃最早未处理的任务 |

---

### 76. 如果不允许丢弃任务，应该选择哪个拒绝策略？

选择 `CallerRunsPolicy`，任务会由提交任务的线程执行，保证任务被执行。

---

### 77. CallerRunsPolicy 有什么风险？

如果任务是耗时操作，可能导致主线程阻塞、线程池阻塞、甚至 OOM。

**解决思路**：
- 增加队列大小和堆内存
- 提高 maximumPoolSize
- 任务持久化到数据库（自定义拒绝策略）

---

### 78. 线程池常用的阻塞队列有哪些？

| 队列 | 线程池 | 特点 |
|------|--------|------|
| LinkedBlockingQueue | Fixed/Single | 无界，可能 OOM |
| SynchronousQueue | Cached | 无容量，最大线程无限 |
| DelayedWorkQueue | Scheduled | 延迟队列，无界 |
| ArrayBlockingQueue | 自定义 | 有界 |

---

### 79. 线程池处理任务的流程？

1. 运行线程数 < 核心线程数 → 新建线程执行
2. 运行线程数 >= 核心线程数 → 加入队列
3. 队列满且运行线程数 < 最大线程数 → 新建线程执行
4. 队列满且运行线程数 >= 最大线程数 → 触发拒绝策略

---

### 80. 线程池中线程异常后，销毁还是复用？

| 提交方式 | 异常处理 | 线程 |
|----------|----------|------|
| execute() | 异常未捕获导致线程终止 | 创建新线程替代 |
| submit() | 异常封装到 Future | 线程继续复用 |

---

### 81. 如何给线程池命名？

使用 `ThreadFactoryBuilder`（Guava）或自定义 `ThreadFactory`：

```java
ThreadFactory factory = new ThreadFactoryBuilder()
    .setNameFormat("worker-%d")
    .build();
```

---

### 82. 如何设定线程池的大小？

- **CPU 密集型**：`N + 1`（N 为 CPU 核心数）
- **IO 密集型**：`2N`

更精确公式：`最佳线程数 = N × (1 + WT/ST)`

---

### 83. 如何动态修改线程池的参数？

使用 `ThreadPoolExecutor` 提供的方法：`setCorePoolSize()`、`setMaximumPoolSize()` 等。

或使用开源框架：Hippo4j、Dynamic TP。

---

### 84. 如何设计优先级线程池？

使用 `PriorityBlockingQueue` 作为任务队列，任务需实现 `Comparable` 或传入 `Comparator`。

注意：可能 OOM、饥饿问题、性能影响。

---

## Future

### 85. Future 类有什么用？

异步思想的典型运用，用于执行耗时任务时不阻塞主线程，获取任务执行结果。

主要方法：`cancel()`、`isCancelled()`、`isDone()`、`get()`。

---

### 86. Callable 和 Future 有什么关系？

`FutureTask` 封装 `Callable` 或 `Runnable`，实现 `Future` 接口。`ExecutorService.submit()` 返回 FutureTask。

---

### 87. CompletableFuture 类有什么用？

解决 `Future` 的局限：
- 不支持异步任务编排组合
- `get()` 方法阻塞调用

`CompletableFuture` 提供了函数式编程、异步任务串联、能力。

---

### 88. 一个任务依赖另外两个任务执行完再执行？

使用 `CompletableFuture.allOf()`：

```java
CompletableFuture.allOf(future1, future2).thenRunAsync(() -> {
    // T3 执行
});
```

---

### 89. CompletableFuture 任务失败如何处理异常？

- `whenComplete`：任务完成时触发回调
- `exceptionally`：处理异常并重新抛出
- `handle`：处理结果和异常并返回新结果
- `allOf`：统一处理所有任务异常

---

### 90. 为什么需要自定义线程池？

`CompletableFuture` 默认使用 `ForkJoinPool.commonPool()`，所有任务共享，高并发时资源竞争导致性能下降。

自定义线程池可提供隔离性、资源控制、异常处理。

---

## AQS

### 91. AQS 是什么？

AQS（AbstractQueuedSynchronizer）是 JDK1.5 提供的并发核心组件，为同步器提供通用框架。

解决了开发者实现同步器的复杂性问题，封装了线程管理逻辑。

---

### 92. AQS 的原理是什么？

AQS 核心思想：
- 资源空闲时，将请求线程设为工作线程，锁定状态
- 资源占用时，将请求线程加入 CLH 变体队列阻塞等待

AQS 使用 `volatile int state` 表示同步状态，通过内置队列完成线程排队。

---

### 93. Semaphore 有什么用？

控制同时访问特定资源的线程数量。

```java
Semaphore semaphore = new Semaphore(5);
semaphore.acquire();  // 获取1个许可
semaphore.release();  // 释放1个许可
```

当 permits=1 时，退化为排他锁。

---

### 94. Semaphore 的原理是什么？

基于 AQS 共享锁实现，`state` 表示许可证数量。

`acquire()`：尝试获取许可证（CAS 减 1），失败则加入阻塞队列
`release()`：释放许可证（CAS 加 1），唤醒队列中一个线程

---

### 95. CountDownLatch 有什么用？

让 N 个线程阻塞在一个地方，直至所有线程任务执行完毕。

一次性，计数器只能在构造方法初始化一次。

---

### 96. CountDownLatch 的原理是什么？

基于 AQS 共享锁实现，`state` 初始化为 count。

`countDown()`：CAS 减 1，直到 0 时唤醒等待线程
`await()`：state 不为 0 时阻塞

---

### 97. CountDownLatch 什么场景下用过？

多线程读取多个文件后汇总结果的场景：

```java
CountDownLatch latch = new CountDownLatch(6);
for (int i = 0; i < 6; i++) {
    threadPool.execute(() -> {
        // 处理文件
        latch.countDown();
    });
}
latch.await();  // 等待所有完成
```

---

### 98. CyclicBarrier 有什么用？

让一组线程到达屏障时被阻塞，直到最后一个线程到达屏障才开门，所有线程继续执行。

可循环使用（Cyclic）。

---

### 99. CyclicBarrier 的原理是什么？

内部使用 `count` 计数器，默认构造方法 `CyclicBarrier(int parties)`。

`await()` 使 count 减 1，当 count=0 时执行 barrierCommand，重置 count。

基于 `ReentrantLock` 和 `Condition` 实现。

---

## 虚拟线程

### 100. 什么是虚拟线程？

JDK 21 正式发布，是 JVM 轻量级线程实现，由平台线程（操作系统线程）调度。

---

### 101. 虚拟线程和平台线程有什么关系？

多个虚拟线程共享一个操作系统线程。虚拟线程是**协作式**调度，平台线程是**抢占式**调度。

---

### 102. 虚拟线程有什么优缺点？

**优点**：
- 极高的创建成本（可创建数百万个）
- 减少线程饥饿
- 简化并发编程

**缺点**：
- 不支持 `ThreadLocal`（需用作用域对象）
- 调试困难
- 不适合 CPU 密集型任务

---

### 103. 如何创建虚拟线程？

```java
// 方式一：Thread.ofVirtual()
Thread virtualThread = Thread.ofVirtual().start(() -> {
    // 任务
});

// 方式二：ThreadFactory
ThreadFactory factory = Thread.ofVirtual().factory();
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
```

---

> 以上内容整理自 JavaGuide (javaguide.cn)
> 共 **103 道面试题**
