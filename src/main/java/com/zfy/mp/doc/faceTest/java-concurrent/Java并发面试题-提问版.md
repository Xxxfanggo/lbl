# Java并发面试题 - 提问版

> 来源：JavaGuide (javaguide.cn)
> 整理：Claude

---

## 线程基础

1. 什么是线程和进程？
2. Java 线程和操作系统的线程有什么区别？
3. 线程与进程的关系、区别及优缺点？
4. 程序计数器为什么是私有的？
5. 虚拟机栈和本地方法栈为什么是私有的？
6. 如何创建线程？
7. 线程的生命周期和状态有哪些？
8. 什么是线程上下文切换？
9. Thread#sleep() 方法和 Object#wait() 方法有什么区别？
10. 为什么 wait() 方法不定义在 Thread 中？
11. 可以直接调用 Thread 类的 run 方法吗？

---

## 多线程基础

12. 并发与并行的区别？
13. 同步和异步的区别？
14. 为什么要使用多线程？
15. 单核 CPU 支持 Java 多线程吗？
16. 单核 CPU 上运行多个线程效率一定会高吗？
17. 使用多线程可能带来什么问题？
18. 如何理解线程安全和不安全？

---

## 死锁

19. 什么是线程死锁？
20. 产生死锁的四个必要条件是什么？
21. 如何检测死锁？
22. 如何预防和避免线程死锁？

---

## JMM与volatile

23. JMM（Java 内存模型）是什么？
24. volatile 关键字如何保证变量的可见性？
25. volatile 如何禁止指令重排序？
26. 什么是内存屏障？有哪几种类型？
27. volatile 读写操作的内存屏障插入策略？
28. volatile 与 happens-before 的关系？
29. volatile 可以保证原子性么？

---

## 乐观锁和悲观锁

30. 什么是悲观锁？
31. 什么是乐观锁？
32. 如何实现乐观锁？（版本号机制、CAS算法）
33. CAS 算法是什么？
34. Java 中 CAS 是如何实现的？
35. CAS 算法存在哪些问题？（ABA问题、循环时间长开销大、只能保证一个共享变量）
36. 乐观锁和悲观锁的区别？

---

## synchronized

37. synchronized 是什么？有什么用？
38. 如何使用 synchronized？（修饰实例方法、静态方法、代码块）
39. 构造方法可以用 synchronized 修饰么？
40. synchronized 底层原理是什么？
41. synchronized 同步语句块的情况？
42. synchronized 修饰方法的情况？
43. JDK1.6 之后的 synchronized 底层做了哪些优化？锁升级原理？
44. synchronized 的偏向锁为什么被废弃了？
45. synchronized 和 volatile 有什么区别？

---

## ReentrantLock

46. ReentrantLock 是什么？
47. 公平锁和非公平锁有什么区别？
48. synchronized 和 ReentrantLock 有什么区别？
49. 可重入锁是什么？
50. ReentrantLock 相比 synchronized 增加了哪些高级功能？
51. 可中断锁和不可中断锁有什么区别？

---

## ReentrantReadWriteLock

52. ReentrantReadWriteLock 是什么？
53. ReentrantReadWriteLock 适合什么场景？
54. 共享锁和独占锁有什么区别？
55. 线程持有读锁还能获取写锁吗？
56. 读锁为什么不能升级为写锁？

---

## StampedLock

57. StampedLock 是什么？
58. StampedLock 有哪几种模式？
59. StampedLock 的性能为什么更好？
60. StampedLock 适合什么场景？

---

## ThreadLocal

61. ThreadLocal 有什么用？
62. ThreadLocal 原理了解吗？
63. ThreadLocal 内存泄露问题是怎么导致的？
64. 为什么 Entry 的 key 设计为弱引用？
65. 如何避免 ThreadLocal 内存泄露？
66. 线程池场景下 ThreadLocal 有什么特殊风险？
67. 如何跨线程传递 ThreadLocal 的值？（InheritableThreadLocal、TransmittableThreadLocal）

---

## 线程池

68. 什么是线程池？
69. 为什么要用线程池？
70. 如何创建线程池？
71. 为什么不推荐使用内置线程池（Executors）？
72. 线程池常见参数有哪些？如何解释？
73. 线程池的核心线程会被回收吗？
74. 核心线程空闲时处于什么状态？
75. 线程池的拒绝策略有哪些？
76. 如果不允许丢弃任务，应该选择哪个拒绝策略？
77. CallerRunsPolicy 拒绝策略有什么风险？如何解决？
78. 线程池常用的阻塞队列有哪些？
79. 线程池处理任务的流程？
80. 线程池中线程异常后，销毁还是复用？
81. 如何给线程池命名？
82. 如何设定线程池的大小？
83. 如何动态修改线程池的参数？
84. 如何设计一个能够根据任务的优先级来执行的线程池？

---

## Future

85. Future 类有什么用？
86. Callable 和 Future 有什么关系？
87. CompletableFuture 类有什么用？
88. 一个任务需要依赖另外两个任务执行完之后再执行，怎么设计？
89. 使用 CompletableFuture，有一个任务失败，如何处理异常？
90. 在使用 CompletableFuture 的时候为什么要自定义线程池？

---

## AQS

91. AQS 是什么？
92. AQS 的原理是什么？
93. Semaphore 有什么用？
94. Semaphore 的原理是什么？
95. CountDownLatch 有什么用？
96. CountDownLatch 的原理是什么？
97. CountDownLatch 什么场景下用过？
98. CyclicBarrier 有什么用？
99. CyclicBarrier 的原理是什么？

---

## 虚拟线程

100. 什么是虚拟线程？
101. 虚拟线程和平台线程有什么关系？
102. 虚拟线程有什么优点和缺点？
103. 如何创建虚拟线程？

---

> 以上内容整理自 JavaGuide (javaguide.cn)
> 共 **103 道面试题**
