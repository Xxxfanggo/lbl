# Java 并发编程面试题

## 高频题索引

以下题目建议优先背熟，面试考察频率更高：

- 【高频】1. 什么是进程和线程？
- 【高频】3. 线程的生命周期和状态有哪些？
- 【高频】8. sleep 和 wait 有什么区别？
- 【高频】14. 什么是线程安全？
- 【高频】16. 什么是死锁？如何避免？
- 【高频】19. 什么是 JMM？
- 【高频】21. 并发编程三大特性是什么？
- 【高频】24. volatile 有什么作用？
- 【高频】26. volatile 能保证原子性吗？
- 【高频】28. synchronized 有什么作用？
- 【高频】30. synchronized 底层原理是什么？
- 【高频】32. synchronized 和 volatile 有什么区别？
- 【高频】34. synchronized 和 ReentrantLock 有什么区别？
- 【高频】39. 什么是 CAS？
- 【高频】41. CAS 有什么问题？
- 【高频】44. ThreadLocal 有什么用？
- 【高频】45. ThreadLocal 原理是什么？
- 【高频】46. ThreadLocal 内存泄漏是怎么回事？
- 【高频】49. 为什么要使用线程池？
- 【高频】51. 为什么不推荐 Executors 创建线程池？
- 【高频】52. 线程池核心参数有哪些？
- 【高频】54. 线程池执行任务流程是什么？
- 【高频】55. 线程池拒绝策略有哪些？
- 【高频】59. 如何设置线程池大小？
- 【高频】64. AQS 是什么？
- 【高频】65. AQS 原理是什么？
- 【高频】69. CompletableFuture 为什么要自定义线程池？

## 1.【高频】什么是进程和线程？

进程：

- 操作系统资源分配的基本单位
- 一个运行中的程序就是一个进程
- 进程之间资源相对独立

线程：

- CPU 调度的基本单位
- 一个进程可以包含多个线程
- 同一进程内线程共享进程资源

面试回答：

进程是操作系统进行资源分配的基本单位，一个运行中的程序通常对应一个进程。线程是 CPU 调度的基本单位，一个进程中可以有多个线程。同一进程内的线程共享堆和方法区等资源，但每个线程有自己的程序计数器、虚拟机栈和本地方法栈。

## 2. Java 线程和操作系统线程有什么关系？

核心：

- 现代 HotSpot JVM 中 Java 线程通常和操作系统线程一一映射
- Java 线程的创建、调度最终依赖操作系统线程机制

面试回答：

在主流 HotSpot JVM 中，Java 线程通常是对操作系统原生线程的封装，基本是一对一关系。Java 创建线程后，底层会创建对应的操作系统线程，线程调度主要由操作系统完成，JVM 在其上提供 Java 层面的线程 API。

## 3.【高频】线程的生命周期和状态有哪些？

Java 线程状态：

```text
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

面试回答：

Java 线程有 6 种状态。NEW 表示线程刚创建还没启动；RUNNABLE 表示可运行，可能正在运行也可能等待 CPU；BLOCKED 表示等待获取锁；WAITING 表示无限期等待其他线程唤醒；TIMED_WAITING 表示带超时时间等待；TERMINATED 表示线程执行结束。

## 4. 创建线程有哪些方式？

常见方式：

- 继承 Thread
- 实现 Runnable
- 实现 Callable 配合 FutureTask
- 使用线程池

面试回答：

Java 创建线程常见方式有继承 Thread、实现 Runnable、实现 Callable 配合 FutureTask，以及使用线程池。实际项目中不建议频繁手动创建线程，更推荐使用线程池统一管理线程资源。

## 5. Runnable 和 Callable 有什么区别？

区别：

- Runnable 没有返回值
- Callable 有返回值
- Runnable 不能直接抛出受检异常
- Callable 可以抛出异常

面试回答：

Runnable 的 run 方法没有返回值，也不能直接抛出受检异常。Callable 的 call 方法可以返回结果，也可以抛出异常，通常配合 Future 或线程池的 submit 方法使用。

## 6. 可以直接调用 Thread 的 run 方法吗？

结论：

可以调用，但不会启动新线程。

面试回答：

直接调用 run 方法只是普通方法调用，代码会在当前线程执行，不会创建新线程。真正启动新线程应该调用 start 方法，start 方法会让 JVM 创建并启动线程，然后由新线程执行 run 方法。

## 7. 什么是线程上下文切换？

定义：

CPU 从一个线程切换到另一个线程执行时，需要保存和恢复线程上下文。

面试回答：

线程上下文切换是指 CPU 从一个线程切换到另一个线程时，需要保存当前线程的执行现场，再恢复另一个线程的执行现场。上下文切换有开销，线程过多或频繁阻塞会导致切换成本变高，影响性能。

## 8.【高频】sleep 和 wait 有什么区别？

区别：

- sleep 是 Thread 的静态方法
- wait 是 Object 的方法
- sleep 不释放锁
- wait 会释放对象锁
- wait 必须在 synchronized 中使用

面试回答：

sleep 用于让当前线程休眠一段时间，不会释放已经持有的锁。wait 用于线程间通信，调用后会释放对象锁，并进入等待状态，需要其他线程调用 notify 或 notifyAll 唤醒。wait 必须在 synchronized 同步代码中使用。

## 9. 为什么 wait 方法定义在 Object 中？

原因：

- wait/notify 依赖对象监视器锁
- 任意对象都可以作为锁

面试回答：

wait 和 notify 是和对象监视器关联的，而 Java 中任意对象都可以作为 synchronized 的锁对象。因此 wait、notify、notifyAll 定义在 Object 中，而不是 Thread 中。

## 10. 并发和并行有什么区别？

区别：

- 并发：多个任务在同一时间段交替执行
- 并行：多个任务在同一时刻同时执行

面试回答：

并发强调多个任务在同一时间段内都在推进，可能是单核 CPU 通过时间片切换实现。并行强调多个任务在同一时刻真正同时执行，通常需要多核 CPU 支持。

## 11. 同步和异步有什么区别？

区别：

- 同步：调用方等待结果返回
- 异步：调用方不阻塞等待，结果通过回调、通知或 Future 获取

面试回答：

同步调用会阻塞等待结果，调用完成后才能继续后续逻辑。异步调用发起后可以先返回，调用方可以继续做其他事情，结果通过回调、Future、消息等方式获取。

## 12. 为什么要使用多线程？

优点：

- 提高 CPU 利用率
- 提高系统吞吐量
- 提升响应速度
- 适合 IO 等待较多的任务

面试回答：

多线程可以让多个任务并发执行，提高 CPU 利用率和系统吞吐量。对于 IO 密集型任务，一个线程等待 IO 时，其他线程可以继续执行，从而提升整体效率。但多线程也会带来线程安全、死锁、上下文切换等问题。

## 13. 单核 CPU 支持多线程吗？

结论：

支持，但不是多个线程真正同时执行。

面试回答：

单核 CPU 也支持多线程，只是多个线程通过时间片轮转交替执行，看起来像同时运行。单核上多线程不一定提升计算性能，但对于 IO 密集型任务仍然有意义，因为一个线程等待 IO 时，CPU 可以执行其他线程。

## 14.【高频】什么是线程安全？

定义：

多线程同时访问共享数据时，程序结果仍然正确。

面试回答：

线程安全指多个线程同时访问同一份共享数据时，无论线程如何调度，程序都能得到正确结果。线程不安全通常是因为多个线程对共享变量进行读写时没有合适的同步控制，导致可见性、原子性或有序性问题。

## 15. 多线程可能带来哪些问题？

常见问题：

- 线程安全问题
- 死锁
- 上下文切换开销
- 资源竞争
- 调试困难

面试回答：

多线程虽然能提升吞吐量和响应速度，但也会带来线程安全、死锁、活锁、资源竞争、上下文切换开销以及问题难复现难排查等问题。因此使用多线程时要控制线程数量，并做好同步和资源管理。

## 16.【高频】什么是死锁？如何避免？

死锁条件：

- 互斥
- 请求并保持
- 不可剥夺
- 循环等待

避免方式：

- 固定加锁顺序
- 避免持有锁时等待其他锁
- 使用超时锁
- 减小锁粒度

面试回答：

死锁是多个线程互相持有对方需要的资源，导致都无法继续执行。死锁产生通常需要互斥、请求并保持、不可剥夺、循环等待四个条件。避免死锁可以破坏这些条件，比如统一加锁顺序、使用 tryLock 超时、减少锁嵌套和缩小锁粒度。

## 17. 如何检测死锁？

方式：

- 使用 jstack 查看线程堆栈
- 使用 jconsole、jvisualvm 等工具
- 线上结合监控和线程 dump 分析

面试回答：

检测死锁常用 jstack 导出线程堆栈，查看是否有线程互相等待锁。也可以使用 JConsole、VisualVM 等工具检测死锁。线上排查时通常会先抓取线程 dump，再分析 BLOCKED 状态和锁持有关系。

## 18. 什么是乐观锁和悲观锁？

悲观锁：

- 认为并发冲突经常发生
- 操作前先加锁

乐观锁：

- 认为冲突较少
- 更新时再检查是否被修改

面试回答：

悲观锁认为并发冲突很可能发生，所以访问共享资源前先加锁，比如 synchronized、ReentrantLock。乐观锁认为冲突概率较低，不提前加锁，而是在更新时检查数据是否被别人修改，常见实现有版本号机制和 CAS。

## 19.【高频】什么是 JMM？

JMM 是 Java Memory Model，Java 内存模型。

核心：

- 定义线程和主内存之间的抽象关系
- 规定可见性、有序性、原子性相关语义
- 屏蔽不同 CPU 和操作系统内存模型差异

面试回答：

JMM 是 Java 内存模型，它不是 JVM 内存区域，而是一套并发语义规范。它定义了线程如何通过主内存进行共享变量读写，以及 volatile、synchronized、final 等关键字如何保证可见性、有序性和原子性。JMM 的作用是屏蔽底层硬件和操作系统差异，让 Java 并发行为有统一规范。

## 20. Java 内存区域和 JMM 有什么区别？

区别：

- Java 内存区域是 JVM 运行时内存划分
- JMM 是并发访问共享变量的规范

面试回答：

Java 内存区域指堆、方法区、虚拟机栈、程序计数器等运行时内存结构。JMM 是 Java 内存模型，关注多线程下共享变量如何读写、如何保证可见性和有序性。一个是内存结构，一个是并发语义规范。

## 21.【高频】并发编程三大特性是什么？

三大特性：

- 原子性
- 可见性
- 有序性

面试回答：

并发编程三大特性是原子性、可见性和有序性。原子性表示操作不可分割；可见性表示一个线程修改共享变量后，其他线程能及时看到；有序性表示程序执行顺序符合预期，不会因为指令重排序导致错误结果。

## 22. 什么是 happens-before？

定义：

happens-before 是 JMM 中判断操作之间可见性和有序性的规则。

面试回答：

happens-before 是 Java 内存模型中的先行发生原则。如果一个操作 happens-before 另一个操作，那么前一个操作的结果对后一个操作可见，并且执行顺序受约束。常见规则包括程序顺序规则、锁规则、volatile 规则、线程启动和终止规则等。

## 23. 什么是指令重排序？

定义：

编译器和处理器为了优化性能，可能在不改变单线程语义的前提下调整指令执行顺序。

面试回答：

指令重排序是编译器或 CPU 为了提高执行效率，对指令顺序进行调整。单线程下通常不会影响结果，但多线程下如果没有同步约束，重排序可能导致其他线程看到不符合预期的中间状态，因此需要 volatile、synchronized 等机制保证有序性。

## 24.【高频】volatile 有什么作用？

作用：

- 保证变量可见性
- 禁止指令重排序

面试回答：

volatile 主要有两个作用：保证可见性和禁止指令重排序。一个线程修改 volatile 变量后，其他线程能及时看到最新值；同时 volatile 读写会插入内存屏障，限制相关指令重排序。但 volatile 不能保证复合操作的原子性。

## 25. volatile 如何保证可见性？

核心：

- 写 volatile 变量会把修改刷新到主内存
- 读 volatile 变量会从主内存读取最新值
- 底层依赖内存屏障

面试回答：

volatile 通过内存屏障和 JMM 规则保证可见性。线程写 volatile 变量时，会把工作内存中的修改刷新到主内存；线程读 volatile 变量时，会从主内存读取最新值。因此其他线程可以及时看到 volatile 变量的修改。

## 26.【高频】volatile 能保证原子性吗？

结论：

不能保证复合操作原子性。

示例：

```java
count++;
```

面试回答：

volatile 不能保证复合操作的原子性。比如 `count++` 包含读取、加一、写回三个步骤，多个线程同时执行仍然可能丢失更新。volatile 适合状态标记、开关变量等场景，如果需要原子自增，可以使用 AtomicInteger 或加锁。

## 27. 为什么双重检查锁单例要加 volatile？

原因：

对象创建可能发生指令重排序。

面试回答：

双重检查锁单例中，创建对象并不是一个原子操作，可能经历分配内存、初始化对象、引用指向内存等步骤。如果发生重排序，其他线程可能拿到一个尚未初始化完成的对象。使用 volatile 修饰实例变量，可以禁止这类重排序，并保证可见性。

## 28.【高频】synchronized 有什么作用？

作用：

- 保证原子性
- 保证可见性
- 保证有序性

面试回答：

synchronized 是 Java 内置锁机制，可以用来修饰方法或代码块。它能保证同一时刻只有一个线程进入同步区域，从而保证原子性；线程释放锁前会刷新共享变量，获取锁后会读取最新值，因此也能保证可见性；同时它还能通过锁语义约束重排序。

## 29. synchronized 可以修饰哪些地方？

用法：

- 修饰实例方法：锁当前对象
- 修饰静态方法：锁 Class 对象
- 修饰代码块：锁指定对象

面试回答：

synchronized 可以修饰实例方法、静态方法和代码块。修饰实例方法时锁的是当前对象；修饰静态方法时锁的是当前类的 Class 对象；修饰代码块时锁的是括号中指定的对象。构造方法不能用 synchronized 修饰。

## 30.【高频】synchronized 底层原理是什么？

核心：

- 同步代码块基于 monitorenter 和 monitorexit
- 同步方法通过方法访问标志实现
- 每个对象都可以关联 Monitor

面试回答：

synchronized 底层基于对象监视器 Monitor 实现。同步代码块编译后会生成 monitorenter 和 monitorexit 字节码指令，线程进入同步块时尝试获取对象 Monitor，退出时释放 Monitor。同步方法则通过方法的访问标志表示，由 JVM 在方法调用时隐式加锁和释放锁。

## 31. synchronized 锁升级过程了解吗？

锁状态：

```text
无锁
偏向锁
轻量级锁
重量级锁
```

面试回答：

JDK 1.6 后 synchronized 做了很多优化，锁会根据竞争情况逐步升级。无竞争时可以使用偏向锁，轻微竞争时使用轻量级锁和 CAS，自旋失败或竞争激烈时升级为重量级锁。锁升级是为了在不同竞争程度下平衡性能和安全性。

## 32.【高频】synchronized 和 volatile 有什么区别？

区别：

- volatile 只能修饰变量
- synchronized 可以修饰方法和代码块
- volatile 保证可见性和有序性，不保证复合操作原子性
- synchronized 可以保证原子性、可见性、有序性

面试回答：

volatile 更轻量，主要用于保证变量可见性和禁止重排序，但不能保证复合操作原子性。synchronized 是锁机制，可以保证同步代码块的原子性、可见性和有序性。简单状态标记可以用 volatile，涉及复合操作或临界区保护要用 synchronized 或其他锁。

## 33. 什么是可重入锁？

定义：

同一个线程获取锁后，可以再次获取同一把锁。

面试回答：

可重入锁指同一个线程已经持有某把锁时，可以再次进入需要同一把锁的代码，不会被自己阻塞。synchronized 和 ReentrantLock 都是可重入锁。可重入锁可以避免递归调用或方法嵌套调用时发生自我死锁。

## 34.【高频】synchronized 和 ReentrantLock 有什么区别？

区别：

- synchronized 是 JVM 内置关键字
- ReentrantLock 是 JUC API
- ReentrantLock 支持公平锁、可中断锁、超时获取锁、多个 Condition
- synchronized 使用更简单，自动释放锁

面试回答：

synchronized 是 Java 内置锁，由 JVM 管理，加锁和释放锁更简单。ReentrantLock 是 JUC 提供的显式锁，需要手动 lock 和 unlock，但功能更强，比如支持公平锁、可中断锁、超时获取锁以及多个 Condition。普通同步优先用 synchronized，需要高级能力时用 ReentrantLock。

## 35. 公平锁和非公平锁有什么区别？

区别：

- 公平锁：按线程等待顺序获取锁
- 非公平锁：允许新线程插队竞争锁

面试回答：

公平锁会尽量按照线程等待顺序获取锁，避免饥饿，但吞吐量通常较低。非公平锁允许后来的线程直接竞争锁，可能导致某些线程等待更久，但减少线程切换，吞吐量通常更高。ReentrantLock 默认是非公平锁。

## 36. 可中断锁和不可中断锁有什么区别？

区别：

- 可中断锁：等待锁时可以响应中断
- 不可中断锁：等待锁时不能被中断打断

面试回答：

可中断锁允许线程在等待锁的过程中响应中断，比如 ReentrantLock 的 lockInterruptibly。不可中断锁在等待锁时不能响应中断，比如 synchronized。可中断锁适合需要取消任务或避免长时间等待的场景。

## 37. ReentrantReadWriteLock 适合什么场景？

适合：

- 读多写少
- 读操作之间可以并发
- 写操作需要独占

面试回答：

ReentrantReadWriteLock 是读写锁，读锁是共享锁，写锁是独占锁。它适合读多写少的场景，多个读线程可以并发执行，但写线程执行时要独占。这样可以提高读多写少场景下的并发性能。

## 38. StampedLock 是什么？

特点：

- Java 8 引入
- 支持写锁、悲观读锁、乐观读
- 乐观读性能较好

面试回答：

StampedLock 是 Java 8 引入的一种锁，提供写锁、悲观读锁和乐观读。它适合读多写少场景，乐观读不会阻塞写线程，只是在读取后校验期间是否发生写操作，因此性能可能比传统读写锁更好。但它不可重入，使用复杂度也更高。

## 39.【高频】什么是 CAS？

CAS 是 Compare And Swap。

包含：

- 内存值
- 期望值
- 新值

面试回答：

CAS 是比较并交换，是一种乐观锁思想。更新变量时，先比较内存中的值是否等于预期值，如果相等就更新为新值，否则更新失败。Java 中很多原子类底层都依赖 CAS 实现。

## 40. Java 中 CAS 是如何实现的？

核心：

- 依赖 Unsafe 或 VarHandle 等底层能力
- 最终通常调用 CPU 原子指令

面试回答：

Java 中 CAS 早期主要通过 Unsafe 类提供的 native 方法实现，底层依赖 CPU 原子指令保证比较和交换操作的原子性。高版本中也可以通过 VarHandle 使用类似能力。AtomicInteger、AtomicLong 等原子类底层就使用了 CAS。

## 41.【高频】CAS 有什么问题？

问题：

- ABA 问题
- 自旋时间长消耗 CPU
- 只能保证单个变量原子操作

面试回答：

CAS 的常见问题有三个。第一是 ABA 问题，变量从 A 变成 B 又变回 A，CAS 可能认为没有变化；第二是高竞争下自旋失败会消耗 CPU；第三是 CAS 通常只能保证单个变量的原子操作，多个变量一致性需要额外机制。

## 42. ABA 问题如何解决？

方式：

- 加版本号
- 使用 AtomicStampedReference
- 使用 AtomicMarkableReference

面试回答：

ABA 问题可以通过版本号解决。每次修改不仅比较值，还比较版本号，只要中间发生过变化，版本号就不同。Java 中可以使用 AtomicStampedReference 来同时维护引用和值的版本号。

## 43. Atomic 原子类有哪些？

常见类型：

- 基本类型：AtomicInteger、AtomicLong、AtomicBoolean
- 数组类型：AtomicIntegerArray、AtomicLongArray
- 引用类型：AtomicReference
- 字段更新器：AtomicIntegerFieldUpdater 等

面试回答：

Atomic 原子类是 JUC 提供的一组原子操作工具，底层多依赖 CAS。常见有 AtomicInteger、AtomicLong、AtomicBoolean、AtomicReference，以及数组类型和字段更新器。它们适合简单共享变量的原子更新场景。

## 44.【高频】ThreadLocal 有什么用？

作用：

- 为每个线程保存独立变量副本
- 避免线程间共享冲突

面试回答：

ThreadLocal 用于保存线程本地变量，每个线程访问到的是自己的副本，线程之间互不影响。常见场景有保存用户上下文、traceId、请求信息、数据库连接等。它不是用来解决共享变量同步，而是通过隔离变量避免共享。

## 45.【高频】ThreadLocal 原理是什么？

核心：

- 每个 Thread 内部有 ThreadLocalMap
- key 是 ThreadLocal 对象
- value 是线程本地变量

面试回答：

ThreadLocal 的数据实际存在线程对象内部的 ThreadLocalMap 中。ThreadLocalMap 的 key 是 ThreadLocal 对象，value 是保存的变量。每个线程都有自己的 ThreadLocalMap，因此不同线程访问同一个 ThreadLocal 时拿到的是各自线程中的值。

## 46.【高频】ThreadLocal 内存泄漏是怎么回事？

原因：

- ThreadLocalMap 的 key 是弱引用
- value 是强引用
- 线程长期存活时，value 可能无法释放

面试回答：

ThreadLocalMap 中 key 是弱引用，如果 ThreadLocal 对象没有外部强引用，GC 后 key 可能变成 null，但 value 仍然被 ThreadLocalMap 强引用。如果线程长期存活，比如线程池线程，value 可能一直无法释放，造成内存泄漏。因此使用 ThreadLocal 后要在 finally 中调用 remove。

## 47. 为什么 ThreadLocalMap 的 key 要用弱引用？

原因：

- 避免 ThreadLocal 对象本身无法被回收
- 减少内存泄漏风险

面试回答：

ThreadLocalMap 的 key 使用弱引用，是为了当外部不再引用 ThreadLocal 对象时，它可以被 GC 回收。否则只要线程存活，ThreadLocalMap 就会一直强引用 ThreadLocal，导致 ThreadLocal 对象无法释放。不过 key 弱引用不能完全避免 value 泄漏，所以仍然要主动 remove。

## 48. 如何跨线程传递 ThreadLocal 的值？

方式：

- InheritableThreadLocal
- TransmittableThreadLocal
- 显式传参

面试回答：

普通 ThreadLocal 不能自动跨线程传递。父子线程场景可以用 InheritableThreadLocal，但在线程池中容易失效，因为线程可能早已创建。线程池异步任务中更常用 TransmittableThreadLocal，或者直接显式传参，显式传参最清晰可靠。

## 49.【高频】为什么要使用线程池？

原因：

- 复用线程，减少创建销毁开销
- 控制并发线程数量
- 统一管理任务和线程
- 提高系统稳定性

面试回答：

线程池可以复用线程，避免频繁创建和销毁线程的开销；也可以控制最大并发线程数，防止线程过多耗尽系统资源。同时线程池提供队列、拒绝策略、线程工厂等机制，方便统一管理异步任务。

## 50. 如何创建线程池？

推荐：

```java
new ThreadPoolExecutor(...)
```

不推荐：

```java
Executors.newFixedThreadPool(...)
```

面试回答：

推荐通过 ThreadPoolExecutor 手动创建线程池，明确核心线程数、最大线程数、队列、线程工厂和拒绝策略。不推荐直接使用 Executors 创建内置线程池，因为其默认队列或最大线程数可能过大，存在 OOM 风险。

## 51.【高频】为什么不推荐 Executors 创建线程池？

原因：

- FixedThreadPool 和 SingleThreadExecutor 使用无界队列
- CachedThreadPool 最大线程数接近无限
- 可能导致 OOM

面试回答：

Executors 创建线程池虽然方便，但隐藏了很多参数。FixedThreadPool 和 SingleThreadExecutor 使用无界队列，任务堆积可能导致 OOM；CachedThreadPool 最大线程数非常大，可能创建过多线程耗尽资源。因此生产环境推荐显式使用 ThreadPoolExecutor。

## 52.【高频】线程池核心参数有哪些？

核心参数：

- corePoolSize
- maximumPoolSize
- keepAliveTime
- unit
- workQueue
- threadFactory
- rejectedExecutionHandler

面试回答：

ThreadPoolExecutor 有七个核心参数：核心线程数、最大线程数、非核心线程空闲存活时间、时间单位、任务队列、线程工厂和拒绝策略。核心线程数控制常驻线程数量，最大线程数控制线程上限，队列用于缓存任务，拒绝策略用于处理无法接收的新任务。

## 53. 线程池常用阻塞队列有哪些？

常见队列：

- ArrayBlockingQueue
- LinkedBlockingQueue
- SynchronousQueue
- PriorityBlockingQueue
- DelayQueue

面试回答：

线程池常用阻塞队列有 ArrayBlockingQueue、LinkedBlockingQueue、SynchronousQueue、PriorityBlockingQueue 等。ArrayBlockingQueue 是有界数组队列，LinkedBlockingQueue 是链表队列，SynchronousQueue 不存储任务，PriorityBlockingQueue 支持优先级任务。

## 54.【高频】线程池执行任务流程是什么？

流程：

```text
核心线程未满 -> 创建核心线程
核心线程已满 -> 放入队列
队列满 -> 创建非核心线程
达到最大线程数 -> 执行拒绝策略
```

面试回答：

线程池提交任务后，如果当前线程数小于核心线程数，会创建核心线程执行任务；如果核心线程已满，会尝试放入任务队列；如果队列也满了，并且线程数还没达到最大线程数，就创建非核心线程；如果线程数已经达到最大值，则执行拒绝策略。

## 55.【高频】线程池拒绝策略有哪些？

内置策略：

- AbortPolicy
- CallerRunsPolicy
- DiscardPolicy
- DiscardOldestPolicy

面试回答：

线程池内置四种拒绝策略。AbortPolicy 直接抛异常；CallerRunsPolicy 由提交任务的线程自己执行；DiscardPolicy 直接丢弃新任务；DiscardOldestPolicy 丢弃队列中最老的任务再尝试提交。生产中通常会自定义拒绝策略，记录日志、告警或降级处理。

## 56. 如果不允许丢弃任务，应该选什么拒绝策略？

思路：

- 可以使用 CallerRunsPolicy 做反压
- 也可以自定义拒绝策略持久化任务或告警

面试回答：

如果任务不能丢弃，可以考虑 CallerRunsPolicy，让提交任务的线程自己执行任务，从而降低提交速度，形成反压。但它可能拖慢调用线程，所以更稳妥的方式是自定义拒绝策略，记录日志、告警，或者把任务写入 MQ/数据库后补偿执行。

## 57. execute 和 submit 有什么区别？

区别：

- execute 没有返回值
- submit 返回 Future
- execute 中异常通常直接抛到线程的异常处理器
- submit 中异常会封装到 Future 中

面试回答：

execute 用于提交 Runnable，没有返回值。submit 可以提交 Runnable 或 Callable，并返回 Future 获取结果。异常处理上也不同，execute 的异常通常会直接表现在线程中，而 submit 的异常会被封装进 Future，调用 get 时才抛出。

## 58. shutdown 和 shutdownNow 有什么区别？

区别：

- shutdown：不再接收新任务，已提交任务继续执行
- shutdownNow：尝试中断正在执行的任务，并返回队列中未执行任务

面试回答：

shutdown 是平滑关闭线程池，不再接收新任务，但已提交任务会继续执行。shutdownNow 会尝试中断正在执行的线程，并返回队列中尚未执行的任务。实际能否停止正在执行的任务，取决于任务是否响应中断。

## 59.【高频】如何设置线程池大小？

思路：

- CPU 密集型：接近 CPU 核心数
- IO 密集型：可以大于 CPU 核心数
- 结合压测和监控调整

面试回答：

线程池大小要根据任务类型设置。CPU 密集型任务主要消耗 CPU，线程数通常设置为 CPU 核心数或核心数加一。IO 密集型任务有大量等待时间，线程数可以适当大一些。最终还是要结合业务耗时、CPU、内存、队列堆积和压测结果调整。

## 60. 如何给线程池命名？

方式：

- 自定义 ThreadFactory
- 设置有业务含义的线程名前缀

面试回答：

线程池应该通过自定义 ThreadFactory 给线程命名，比如 `order-pool-1`、`log-pool-1`。这样线上排查问题时，通过日志或线程 dump 可以快速判断线程属于哪个业务线程池。

## 61. 线程池中线程异常后会销毁还是复用？

结论：

执行任务时抛出未捕获异常，工作线程通常会结束，线程池会补充新线程。

面试回答：

线程池中的工作线程执行任务时，如果抛出未捕获异常，当前工作线程通常会终止。线程池会根据需要创建新的工作线程补充。为了避免异常被吞掉或难排查，任务内部应做好异常捕获和日志记录。

## 62. Future 有什么用？

作用：

- 表示异步任务结果
- 可以查询任务状态
- 可以获取结果或取消任务

面试回答：

Future 用于表示异步任务的执行结果。提交 Callable 到线程池后会返回 Future，可以通过 get 获取结果，通过 cancel 取消任务，也可以判断任务是否完成。但 Future 的 get 是阻塞的，组合多个异步任务不够方便。

## 63. Callable 和 Future 有什么关系？

关系：

- Callable 表示有返回值的任务
- Future 表示任务执行结果

面试回答：

Callable 是一个可以返回结果的任务接口，Future 是这个任务异步执行后的结果句柄。把 Callable 提交给线程池后，会得到一个 Future，通过 Future 可以获取 Callable 的执行结果。

## 64.【高频】AQS 是什么？

AQS 是 AbstractQueuedSynchronizer。

作用：

- 构建锁和同步器的基础框架
- ReentrantLock、Semaphore、CountDownLatch 等都基于 AQS

面试回答：

AQS 是 JUC 中非常核心的同步器框架，很多锁和同步工具都基于它实现，比如 ReentrantLock、Semaphore、CountDownLatch、ReentrantReadWriteLock。它封装了同步状态管理、线程排队、阻塞和唤醒等通用逻辑。

## 65.【高频】AQS 原理是什么？

核心：

- 一个 volatile state 表示同步状态
- 一个 FIFO 等待队列
- CAS 修改 state
- LockSupport 挂起和唤醒线程

面试回答：

AQS 的核心是一个 volatile 修饰的 state 状态变量和一个 FIFO 等待队列。线程获取资源失败时，会被封装成 Node 节点加入等待队列，并通过 LockSupport 挂起；释放资源时会唤醒后继节点。具体如何获取和释放资源由子类实现 tryAcquire、tryRelease 等方法。

## 66. AQS 独占模式和共享模式有什么区别？

区别：

- 独占模式：同一时刻只有一个线程获取资源
- 共享模式：多个线程可以同时获取资源

面试回答：

AQS 支持独占和共享两种模式。独占模式下同一时刻只有一个线程能获取资源，比如 ReentrantLock。共享模式下多个线程可以同时获取资源，比如 Semaphore、CountDownLatch。不同同步器会给 state 赋予不同含义。

## 67. Semaphore、CountDownLatch、CyclicBarrier 有什么区别？

区别：

- Semaphore：控制同时访问资源的线程数量
- CountDownLatch：一个或多个线程等待其他线程完成
- CyclicBarrier：一组线程互相等待到达屏障点

面试回答：

Semaphore 用于限流或控制并发访问数量。CountDownLatch 是倒计时器，适合主线程等待多个子任务完成，计数不能重置。CyclicBarrier 是循环屏障，适合一组线程相互等待，所有线程到齐后再继续执行，并且可以重复使用。

## 68. CompletableFuture 有什么用？

作用：

- 异步执行任务
- 编排多个异步任务
- 支持结果转换、组合、异常处理

面试回答：

CompletableFuture 是 Java 8 提供的异步编排工具，比 Future 更强大。它不仅能异步执行任务，还能对结果进行转换、组合多个异步任务、处理异常，适合多个远程调用并行执行后汇总结果的场景。

## 69.【高频】CompletableFuture 为什么要自定义线程池？

原因：

- 默认使用公共 ForkJoinPool
- 可能和其他业务任务互相影响
- 不方便隔离、监控和调参

面试回答：

CompletableFuture 如果不指定线程池，默认可能使用公共 ForkJoinPool。这样不同业务的异步任务会混在一起，容易互相影响，也不利于监控和调参。生产环境建议传入自定义线程池，按业务隔离，并设置合理的线程名、队列和拒绝策略。

## 70. 什么是虚拟线程？

定义：

虚拟线程是 JDK 21 正式引入的轻量级线程。

面试回答：

虚拟线程是 Java 提供的轻量级线程，由 JVM 调度，底层运行在少量平台线程之上。它创建成本低，适合大量阻塞式 IO 场景，可以用同步代码写出高并发效果。但虚拟线程不是为了提升 CPU 密集型任务性能，也不能替代所有线程池设计。

